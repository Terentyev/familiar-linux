SECTION = "libs"
LICENSE = "LGPL"
PRIORITY = "optional"
MAINTAINER = "Phil Blundell <pb@debian.org>"
DEPENDS = "libsvg cairo"
DESCRIPTION = "SVG rendering library"
PR = "r1"

SRC_URI = "http://cairographics.org/snapshots/libsvg-cairo-${PV}.tar.gz"

inherit autotools pkgconfig 

do_stage () {
	autotools_stage_all
}
