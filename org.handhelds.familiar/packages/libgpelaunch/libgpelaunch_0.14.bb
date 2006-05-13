LICENSE = "LGPL"
DESCRIPTION = "GPE program launcher library"
SECTION = "libs"
DEPENDS = "gtk+ libgpewidget startup-notification"

inherit autotools pkgconfig

SRC_URI = "${GPE_MIRROR}/${PN}-${PV}.tar.bz2"

do_stage() {
autotools_stage_all
}
