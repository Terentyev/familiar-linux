DESCRIPTION = "RTC alarm handling library for GPE"
SECTION = "gpe/libs"
PRIORITY = "optional"
LICENSE = "LGPL"
PR = "r1"

DEPENDS = "glib-2.0 sqlite"

GPE_TARBALL_SUFFIX = "bz2"
inherit autotools pkgconfig gpe 


do_stage () {
autotools_stage_all
}

