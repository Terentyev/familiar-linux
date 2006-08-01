# debian-vampyre.bbclass - the supermarket thing
#
# Copyright (C) 2006, Rene Wagner
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)
#

PN = "${@bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[0] or 'defaultpkgname'}"
PV = "${@'-'.join((bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[1] or '1.0').split('-')[:-1])}"
PR = "${@(bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[1] or '1').split('-')[-1]}"

DEBIAN_ARCHIVE ?= "main"
DEBIAN_BASE_URI = "${DEBIAN_MIRROR}/${DEBIAN_ARCHIVE}/${@bb.data.getVar('PN', d, 1)[0]}/${PN}"
SRC_URI = "${DEBIAN_BASE_URI}/${PN}_${PV}.orig.tar.gz \
           ${DEBIAN_BASE_URI}/${PN}_${PV}-${PR}.diff.gz;patch=1"

do_install_append () {
	mkdir -p ${D}${datadir}/doc/${PN}
	rm -f ${D}${datadir}/doc/${PN}/changelog.Debian*
	install -m 0644 debian/changelog ${D}${datadir}/doc/${PN}/changelog.Debian
	gzip -9 ${D}${datadir}/doc/${PN}/changelog.Debian
	[ -f debian/README.Debian ] && install -m 0644 debian/README.Debian ${D}${datadir}/doc/${PN}/
}
