PV = "0.0cvs${CVSDATE}"
LICENSE = "BSD-X"
SECTION = "x11/libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@freedesktop.org>"
DEPENDS = "x11 xcalibrateext xext"
DESCRIPTION = "XCalibrate client-side library"
PR = "r1"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xlibs;module=XCalibrate"
S = "${WORKDIR}/XCalibrate"

inherit autotools pkgconfig 

do_stage() {
	autotools_stage_all
}
