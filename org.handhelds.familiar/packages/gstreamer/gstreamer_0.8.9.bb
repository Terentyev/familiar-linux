DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound."
SECTION = "base"
PRIORITY = "optional"
LICENSE = "LGPL"
PR = "r5"

DEPENDS = "glib-2.0 gettext-native popt"

FILES_${PN} += " ${libdir}/gstreamer-0.8/*.so"
FILES_${PN}-dev += " ${libdir}/gstreamer-0.8/*.la ${libdir}/gstreamer-0.8/*.a"

SRC_URI = "http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.bz2 \
	file://libm.patch;patch=1 \
	file://no-libxml2.patch;patch=1 \
	file://filesrc-uri.patch;patch=1 \
	file://gstreamer.xsession"

EXTRA_OECONF = "--disable-docs-build --disable-dependency-tracking --disable-loadsave"

inherit autotools pkgconfig

do_stage() {
	autotools_stage_all
}

do_install_append() {
	install -d ${D}${sysconfdir}/X11/Xsession.d
	install ${WORKDIR}/gstreamer.xsession ${D}${sysconfdir}/X11/Xsession.d/90gst-register
}

