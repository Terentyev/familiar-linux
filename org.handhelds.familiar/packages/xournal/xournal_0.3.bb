DESCRIPTION = "A stylus driven notetaking and journaling application"
DEPENDS = "gtk+ libgnomecanvas"
LICENSE = "GPL"

SRC_URI = "http://math.mit.edu/~auroux/software/${PN}/${PN}-${PV}.tar.gz \
           file://no-printing.diff;patch=1 \
	   file://xournal.desktop"

inherit autotools

PACKAGES = "${PN}-doc ${PN}"
FILES_${PN}-doc = "${datadir}/${PN}/html-doc"

do_install_append () {
	install -d ${D}${datadir}/applications
	install -m 644 ${WORKDIR}/xournal.desktop ${D}${datadir}/applications
	install -d ${D}${datadir}/pixmaps
	ln -s ../${PN}/pixmaps/notepad.png ${D}${datadir}/pixmaps/xournal.png
}
