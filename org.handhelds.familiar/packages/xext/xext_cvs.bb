PV = "0.0cvs${CVSDATE}"
PR = "r2"
LICENSE= "MIT"
DESCRIPTION = "X Server Extension library"
SECTION = "x11/libs"
PRIORITY = "optional"
DEPENDS = "xproto x11 xextensions"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=Xext"
S = "${WORKDIR}/Xext"

inherit autotools pkgconfig 

do_stage() {
	autotools_stage_all
}
