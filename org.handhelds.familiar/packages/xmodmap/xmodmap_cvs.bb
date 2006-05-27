DESCRIPTION = "utility for modifying keymaps and pointer button mappings in X"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "x11/base"
LICENSE = "MIT"

PV = "0.0cvs${CVSDATE}"
PR = "r2"

DEPENDS = "x11"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xorg;module=xc/programs/xmodmap \
           file://autofoo.patch;patch=1"
S = "${WORKDIR}/xmodmap"

inherit autotools pkgconfig 
