DESCRIPTION = "library for converting characters to X key-presses"
LICENSE = "GPL"
DEPENDS = "xtst"
SECTION = "x11/wm"

SRC_URI = "http://projects.o-hand.com/matchbox/sources/${PN}/${@'.'.join(bb.data.getVar('PV', d, 1).split('.')[:2])}/${PN}-${PV}.tar.bz2"

inherit autotools pkgconfig gettext

FILES_${PN} = "${libdir} \
	       ${datadir}/applications \
	       ${datadir}/pixmaps"

do_stage () {
	install -d ${STAGING_INCDIR}/fakekey	
	install -m 0644 ${S}/fakekey/fakekey.h ${STAGING_INCDIR}/fakekey
	oe_libinstall -so -C src libfakekey ${STAGING_LIBDIR}		
}

