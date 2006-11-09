# libgtk-java OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Gtk+ Java bindings"
HOMEPAGE = "http://java-gnome.sf.net"
LICENSE = "LGPL"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
PR = "r2"

DEPENDS = "glib-2.0 gtk+ libart-lgpl pango"
RDEPENDS_${PN} = "${PN}-common (>= ${PV})"

inherit gnome java pkgconfig

SRC_URI += "${DEBIAN_MIRROR}/main/libg/${PN}/${PN}_${PV}-1.diff.gz;patch=1 \
	file://textbuffer.patch;patch=1 \
	file://pc.patch;patch=1"

EXTRA_OECONF = "--without-javadocs --without-gcj-compile"

PACKAGES =+ "${PN}-common ${PN}-examples"

FILES_${PN} = "${libdir}/lib*so"
FILES_${PN}-common = "${datadir}/java"
FILES_${PN}-dev += "${datadir}/${PN}/macros"
FILES_${PN}-examples = "${datadir}/doc/${PN}/examples"

do_stage () {
	autotools_stage_all

	install -d ${STAGING_DATADIR}/aclocal/jg-macros
	for i in macros/*; do
		install -m 0644 $i ${STAGING_DATADIR}/aclocal/jg-macros/
	done

	mkdir -p ${STAGING_DATADIR}/java
	for i in *.jar; do
		install -m 0644 $i ${STAGING_DATADIR}/java/`basename $i .jar`-${PV}.jar
		(cd ${STAGING_DATADIR}/java && ln -sf `basename $i .jar`-${PV}.jar $i)
	done
}
