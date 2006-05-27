DESCRIPTION = "server access control program for X"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "x11/base"
LICENSE = "MIT"

FIXEDCVSDATE = "${@bb.data.getVar('FILE', d, 1).split('_')[-1].split('.')[0]}"
PV = "0.0cvs${FIXEDCVSDATE}"
PR = "r3"

DEPENDS = "x11 xext xmu"

SRC_URI = "cvs://anoncvs:anoncvs@pdx.freedesktop.org/cvs/xorg;module=xc/programs/xhost;date=${FIXEDCVSDATE} \
           file://autofoo.patch;patch=1"
S = "${WORKDIR}/xhost"

inherit autotools pkgconfig 
