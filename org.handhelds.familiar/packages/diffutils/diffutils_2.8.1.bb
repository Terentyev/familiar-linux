DESCRIPTION = "Diffutils contains the GNU diff, diff3, \
sdiff, and cmp utilities. These programs are usually \
used for creating patch files."
SECTION = "base"
LICENSE = "GPL"
PR = "r1"

SRC_URI = "${GNU_MIRROR}/diffutils/diffutils-${PV}.tar.gz"

inherit autotools

PACKAGES += "diff"
FILES_diff = "${bindir}/diff"
RSUGGESTS_diff = "${PN}"
FILES_${PN} = "${bindir}/cmp \
	       ${bindir}/diff3 \
	       ${bindir}/sdiff"
RRECOMMENDS_${PN} = "diff"
