inherit rootfs_ipk

# Images are generally built explicitly, do not need to be part of world.
EXCLUDE_FROM_WORLD = "1"

USE_DEVFS ?= "0"

DEPENDS += "makedevs-native"

def get_image_deps(d):
	import bb
	str = ""
	for type in (bb.data.getVar('IMAGE_FSTYPES', d, 1) or "").split():
		deps = bb.data.getVar('IMAGE_DEPENDS_%s' % type, d) or ""
		if deps:
			str += " %s" % deps
	return str

DEPENDS += "${@get_image_deps(d)}"

IMAGE_DEVICE_TABLE ?= "${@bb.which(bb.data.getVar('BBPATH', d, 1), 'files/device_table-minimal.txt')}"
IMAGE_POSTPROCESS_COMMAND ?= ""

# Must call real_do_rootfs() from inside here, rather than as a separate
# task, so that we have a single fakeroot context for the whole process.
fakeroot do_rootfs () {
	set -x
	rm -rf ${IMAGE_ROOTFS}

	if [ "${USE_DEVFS}" != "1" ]; then
		mkdir -p ${IMAGE_ROOTFS}/dev
		makedevs -r ${IMAGE_ROOTFS} -D ${IMAGE_DEVICE_TABLE}
	fi

	real_do_rootfs

	insert_feed_uris	

	rm -f ${IMAGE_ROOTFS}${libdir}/ipkg/lists/oe
	
	${IMAGE_PREPROCESS_COMMAND}
		
	export TOPDIR=${TOPDIR}

	for type in ${IMAGE_FSTYPES}; do
		if test -z "$FAKEROOTKEY"; then
			fakeroot -i ${TMPDIR}/fakedb.image bbimage -t $type -e ${FILE}
		else
			bbimage -n "${IMAGE_NAME}" -t "$type" -e "${FILE}"
		fi
	done

	${IMAGE_POSTPROCESS_COMMAND}
}

DISTRO_FEEDS_FILE_PATTERN = "${IMAGE_ROOTFS}/etc/ipkg/${DISTRO}-${DISTRO_VERSION}-%s.conf"

python __anonymous() {
	m = bb.data.getVar("MACHINE", d, 1)
	pattern = bb.data.getVar("DISTRO_FEEDS_FILE_PATTERN", d, 1)
	pfx = bb.data.getVar("DISTRO_FEEDS_PREFIX", d, 1)
	cs = bb.data.getVar("DISTRO_FEEDS_COLLECTIONS", d, 1).split()
	
	cmds = ""
	for c in cs:
		file = pattern % c
		cmds += "cat > %s <<EOF\n" % file

		desc = bb.data.getVar("DISTRO_FEEDS_COLLECTION_DESC_%s" % c, d, 1)
		cmds += "# %s\n\n" % desc

		feeds = bb.data.getVar("DISTRO_FEEDS_IN_%s" % c, d, 1).split()
		for f in feeds:
			desc = bb.data.getVar("DISTRO_FEEDS_FEED_DESC_%s" % f, d, 1)
			cmds += "# %s - %s\n" % (f, desc)
			cmds += "src/gz %s %s/%s/%s\n" % (f, pfx, c, f)
			cmds += "src/gz %s-%s %s/%s/%s/machine/%s\n\n" % (f, m, pfx, c, f, m)

		# locale subfeeds

		# add template
		cmds += "\n# For each supported locale there is a subfeed in each of the feed folders.\n"
		cmds += "# You can use your webbrowser to check for valid locale codes.\n\n"
		cmds += "# To point ipkg at packages for your locale, replace <my_locale> with the\n"
		cmds += "# locale code in the template below and remove the leading '#' characters.\n\n"
		for f in feeds:
			cmds += "# src/gz %s-locale-<my_locale> %s/%s/locale/<my_locale>\n" % (f, pfx, f)
		
		# add feed for each IMAGE_LINGUA
		linguas = bb.data.getVar("IMAGE_LINGUAS", d, 1).split()
		for l in linguas:
			fst = l.split('-')[0]
			cmds += "\n# %s locale feeds\n" % fst
			for f in feeds:
				cmds += "src/gz %s-locale-%s %s/%s/locale/%s\n" % (f, fst, pfx, f, fst)
		
		cmds += "\nEOF\n"

	bb.data.setVar("DISTRO_FEEDS_COMMANDS", cmds, d)
}

insert_feed_uris () {
	
	echo "Building feeds for [${DISTRO}].."
		
${DISTRO_FEEDS_COMMANDS}

	mkdir -p ${DEPLOY_DIR}/ipkg_confs/${MACHINE}
	cp -f ${IMAGE_ROOTFS}/etc/ipkg/*conf ${DEPLOY_DIR}/ipkg_confs/${MACHINE}
}
