# jamvm-native OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

include jamvm_${PV}.bb
inherit native

DEPENDS = "classpath-native"

SRC_URI = "${SOURCEFORGE_MIRROR}/jamvm/jamvm-${PV}.tar.gz"

prefix = "${STAGING_DIR}/${HOST_SYS}"
EXTRA_OECONF = "--with-classpath-install-dir=${STAGING_DIR}/${HOST_SYS}"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/jamvm"
S = "${WORKDIR}/jamvm-${PV}"

do_stage() {
	mkdir -p ${STAGING_BINDIR}
	install -m 0755 src/jamvm ${STAGING_BINDIR}/

	mkdir -p ${STAGING_DATADIR}/jamvm
	install -m 0644 lib/inst_classes.zip ${STAGING_DATADIR}/jamvm/classes.zip
}

