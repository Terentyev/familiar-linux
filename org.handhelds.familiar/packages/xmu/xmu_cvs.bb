PV = "0.0cvs${CVSDATE}"
LICENSE = "MIT"
SECTION = "x11/libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
DEPENDS = "xt xext"
PR = "r3"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=Xmu"
S = "${WORKDIR}/Xmu"

PACKAGES =+ "xmuu xmuu-dev"

FILES_xmuu = "${libdir}/libXmuu.so.*"
FILES_xmuu-dev = "${libdir}/libXmuu.so"

inherit autotools pkgconfig 

do_stage () {
	autotools_stage_all
}
