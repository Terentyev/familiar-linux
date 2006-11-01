# classpath-native OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

# NOTE: This is supposed to be used in conjunction with a VM (JamVM)
#       to run build tools only. Don't compile any target java code
#       against this!

include classpath_${PV}.bb
inherit native

DEPENDS = "jikes-native zip-native"

EXTRA_OECONF += "--disable-gtk-peer"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/classpath"
S = "${WORKDIR}/classpath-${PV}"

STAGE_TEMP="${WORKDIR}/temp-staging"

do_stage() {
	rm -rf ${STAGE_TEMP}
	mkdir -p ${STAGE_TEMP}
	make DESTDIR="${STAGE_TEMP}" install

	# stage class library
	mkdir -p ${STAGING_DATADIR}/classpath
	for i in `find ${STAGE_TEMP} -name 'glibj.zip'`; do
		install -m 0644 $i ${STAGING_DATADIR}/classpath
	done

	# stage JNI libraries
	mkdir -p ${STAGING_LIBDIR}/classpath
	for i in `find ${STAGE_TEMP} -name '*.so*'`; do
		install -m 0644 $i ${STAGING_LIBDIR}/classpath
	done

	rm -rf ${STAGE_TEMP}
}

