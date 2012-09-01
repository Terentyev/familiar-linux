include coreutils_${PV}.bb
inherit native

SRC_URI += "file://fixes-gcc-4.4.patch;patch=1"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/coreutils-${PV}"
S = "${WORKDIR}/coreutils-${PV}"
