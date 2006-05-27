LICENSE = "GPL"
DESCRIPTION = "Touchscreen calibration utility"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
SECTION = "x11/base"
PR = "r2"

DEPENDS = "x11 libxft libxrandr xcalibrate"


SRC_URI = "${GPE_MIRROR}/xtscal-${PV}.tar.bz2 \
           file://xtscal-cxk.patch;patch=1"

inherit autotools
