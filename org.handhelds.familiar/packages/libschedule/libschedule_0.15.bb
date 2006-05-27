DESCRIPTION = "RTC alarm handling library for GPE"
SECTION = "gpe/libs"
PRIORITY = "optional"
LICENSE = "LGPL"
PR = "r4"

DEPENDS = "glib-2.0 sqlite"

inherit autotools pkgconfig gpe 


do_stage () {
autotools_stage_all
}

