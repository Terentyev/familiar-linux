DESCRIPTION = "Volume Control Applet for GPE"
LICENSE = "GPL"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "gpe"
PR = "r2"

DEPENDS = "libgpewidget"

GPE_TARBALL_SUFFIX = "bz2"
inherit gpe autotools

SRC_URI += "file://disable-polling.patch;patch=1"
