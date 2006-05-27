DESCRIPTION = "Gesture recognition input method for X11"
LICENSE = "GPL"
SECTION = "x11"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
PR = "r3"

DEPENDS = "x11 xft xtst xpm"

SRC_URI = "http://www.oesources.org/source/xstroke-0.6.tar.gz \
	file://auto-disable.patch;patch=1;pnum=0"

inherit autotools pkgconfig 
