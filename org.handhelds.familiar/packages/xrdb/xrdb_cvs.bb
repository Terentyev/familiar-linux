DESCRIPTION = "X server resource database utility"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "x11/base"
LICENSE = "xrdb"

PV = "0.0cvs${CVSDATE}"
PR = "r3"

DEPENDS = "x11 xmu xext"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xorg;module=xc/programs/xrdb \
           file://autofoo.patch;patch=1"
S = "${WORKDIR}/xrdb"

inherit autotools pkgconfig 
