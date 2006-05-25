SECTION = "libs"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@debian.org>"
DEPENDS = "x11"
DESCRIPTION = "Cairo support library"
LICENSE = "X11"
SRC_URI = "http://cairographics.org/snapshots/libpixman-${PV}.tar.gz"
PR = "r1"

inherit autotools pkgconfig 

do_stage () {
	autotools_stage_all
}
