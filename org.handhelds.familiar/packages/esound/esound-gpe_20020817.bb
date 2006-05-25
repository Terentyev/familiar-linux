DESCRIPTION = "Enlightened Sound Daemon - GPE version"
SECTION = "gpe/base"
LICENSE = "GPL"
DEPENDS = "audiofile"
PR = "r4"

CVSDATE = "${PV}"
SRC_URI = "${HANDHELDS_CVS};module=gpe/base/esound \
	   file://audiofile-please.patch;patch=1 \
	   file://configure.patch;patch=1"

S = "${WORKDIR}/esound"

inherit autotools binconfig pkgconfig

EXTRA_OECONF = "--disable-alsa"

do_stage () {
	install -m 0644 esd.h ${STAGING_INCDIR}/esd.h
	oe_libinstall -C .libs -so libesd ${STAGING_LIBDIR}
	install -m 0644 esd.m4 ${STAGING_DATADIR}/aclocal
}

PACKAGES =+ "esddsp esd esd-utils"

FILES_esddsp = "${bindir}/esddsp ${libdir}/libesddsp.so.*"
FILES_esd = "${bindir}/esd"
FILES_esd-utils = "${bindir}"
