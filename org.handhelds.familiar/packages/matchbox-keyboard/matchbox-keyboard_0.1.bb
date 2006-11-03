DESCRIPTION = "Matchbox keyboard"
LICENSE = "GPL"
DEPENDS = "libfakekey expat libxft"
SECTION = "x11/wm"

SRC_URI = "http://projects.o-hand.com/matchbox/sources/${PN}/${@'.'.join(bb.data.getVar('PV', d, 1).split('.')[:2])}/${PN}-${PV}.tar.bz2 \
           file://fontsize.patch;patch=1"

inherit autotools pkgconfig gettext

FILES_${PN} = "${bindir} \
	       ${datadir}/applications \
	       ${datadir}/pixmaps \
	       ${datadir}/matchbox-keyboard"
	
