DESCRIPTION = "The GNU compression utility"
LICENSE = "GPL"
SECTION = "console/utils"
PRIORITY = "required"

inherit autotools debian-vampyre

SRC_URI += "file://configure.patch;patch=1"

base_bindir_apps = "gunzip gzip zcat"

do_install () {
	autotools_do_install
	# Rename and move files into /bin (FHS)
	install -d ${D}${base_bindir}
	for i in ${base_bindir_apps}; do
		mv ${D}${bindir}/$i ${D}${base_bindir}/$i.${PN}
	done
}

pkg_postinst_${PN} () {
	for i in ${base_bindir_apps}; do
		update-alternatives --install ${base_bindir}/$i $i $i.${PN} 100
	done
}

pkg_prerm_${PN} () {
	for i in ${base_bindir_apps}; do
		update-alternatives --remove $i $i.${PN}
	done
}

