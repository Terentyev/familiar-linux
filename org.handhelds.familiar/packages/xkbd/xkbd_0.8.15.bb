DESCRIPTION = "A small, highly configurable virtual on-screen keyboard for X11"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
SECTION = "x11"
LICENSE = "GPL"
PR = "r3"

DEPENDS = "xpm xtst libxft"

SRC_URI = "http://handhelds.org/~mallum/xkbd/xkbd-${PV}-CVS.tar.gz \
           file://libtool-lossage.patch;patch=1;pnum=1 \
	   file://fix-equalsign.patch;patch=1 \
	   file://fix-circumkey.patch;patch=1 \
	   file://xkbd.png"

inherit autotools

do_install_append() {
	install -d ${D}${datadir}/pixmaps
	install -m 0644 ${WORKDIR}/xkbd.png ${D}${datadir}/pixmaps/
}

