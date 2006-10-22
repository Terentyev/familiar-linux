RDEPENDS += "kernel (${KERNEL_VERSION})"
DEPENDS += "virtual/kernel"

inherit module-base

KERNEL_MAJOR_VERSION = "${@'.'.join(bb.data.getVar('KERNEL_VERSION', d, 1).split('.')[:2])}"

python populate_packages_prepend() {
	v = bb.data.getVar("PARALLEL_INSTALL_MODULES", d, 1) or "0"
	if v == "1":
		kv = bb.data.getVar("KERNEL_MAJOR_VERSION", d, 1)
		packages = bb.data.getVar("PACKAGES", d, 1)
		repl_vers = bb.data.getVar("PARALLEL_INSTALL_REPLACE_VERSIONS", d, 1)
		for p in packages.split():
			pkg = bb.data.getVar("PKG_%s" % p, d, 1) or p
			newpkg = "%s-%s" % (pkg, kv)
			bb.data.setVar("PKG_%s" % p, newpkg, d)
			rprovides = bb.data.getVar("RPROVIDES_%s" % p, d, 1)
			if rprovides:
				rprovides = "%s %s" % (rprovides, pkg)
			else:
				rprovides = pkg
			bb.data.setVar("RPROVIDES_%s" % p, rprovides, d)

			# kv was changed from KERNEL_VERSION to KERNEL_MAJOR_VERSION.
			# now fix the upgrade path...
			if repl_vers:
				repl_pkgs = []
				for v in repl_vers.split():
					repl_pkgs.append("%s-%s" % (pkg, v))
				for i in ["PROVIDES", "CONFLICTS", "REPLACES"]:
					val = bb.data.getVar("R%s_%s" % (i, p), d, 1)
					if val:
						old = val.split()
						add = []
						for k in repl_pkgs:
							if not k in old:
								add.append(k)
						val = "%s %s" % (val, " ".join(add))
					else:
						val = "%s" % " ".join(repl_pkgs)
					bb.data.setVar("R%s_%s" % (i, p), val, d)
}

module_do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake KERNEL_PATH=${STAGING_KERNEL_DIR}   \
		   KERNEL_SRC=${STAGING_KERNEL_DIR}    \
		   KERNEL_VERSION=${KERNEL_VERSION}    \
		   CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
		   ${MAKE_TARGETS}
}

module_do_install() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake DEPMOD=echo INSTALL_MOD_PATH="${D}" CC="${KERNEL_CC}" LD="${KERNEL_LD}" modules_install
}

pkg_postinst_append () {
	if [ -n "$D" ]; then
		exit 1
	fi
	depmod -A
	update-modules || true
}

pkg_postrm_append () {
	update-modules || true
}

EXPORT_FUNCTIONS do_compile do_install

FILES_${PN} = "/etc /lib/modules"
