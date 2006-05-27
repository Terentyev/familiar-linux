DESCRIPTION = "X authority file utility"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "x11/base"
LICENSE = "MIT"

PV = "0.0cvs${CVSDATE}"
PR = "r3"

DEPENDS = "x11 xau xmu xext"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xorg;module=xc/programs/xauth \
           file://autofoo.patch;patch=1"
S = "${WORKDIR}/xauth"

inherit autotools pkgconfig 
