# libglade-java OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Glade Java bindings"
HOMEPAGE = "http://java-gnome.sf.net"
LICENSE = "LGPL"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
PR = "r1"

DEPENDS = "libglade libgtk-java"
RDEPENDS_${PN} = "${PN}-common (>= ${PV})"

inherit gnome java

EXTRA_OECONF = "--without-javadocs --without-gcj-compile --without-gnome"

EXTRA_AUTORECONF += "-I ${STAGING_DATADIR}/aclocal/jg-macros"

PACKAGES =+ "${PN}-common ${PN}-examples"

FILES_${PN} = "${libdir}/lib*so"
FILES_${PN}-common = "${datadir}/java"
FILES_${PN}-dev += "${datadir}/${PN}/macros"
FILES_${PN}-examples = "${datadir}/doc/${PN}/examples"

