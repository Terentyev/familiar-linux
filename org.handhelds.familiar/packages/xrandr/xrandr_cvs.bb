DESCRIPTION = "X Resize and Rotate extension command."
SECTION = "x11/base"
LICENSE= "BSD-X"

PV = "0.0cvs${CVSDATE}"
PR = "r1"

DEPENDS = "libxrandr x11 xext"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xapps;module=xrandr"
S = "${WORKDIR}/xrandr"

inherit autotools pkgconfig 
