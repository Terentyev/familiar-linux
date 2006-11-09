# swt-gtk OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "The Standard Widget Toolkit"
HOMEPAGE = "http://www.eclipse.org/swt/"
LICENSE = "CPL/MPL/LGPL"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "libs"

DEPENDS = "glib-2.0 gtk+ libart-lgpl pango xtst"

SRC_URI = "${DEBIAN_MIRROR}/main/s/swt-gtk/swt-gtk_3.1.orig.tar.gz \
	${DEBIAN_MIRROR}/main/s/swt-gtk/swt-gtk_3.1-2.diff.gz;patch=1 \
	file://no-native-no-files-cruft.patch;patch=1"

inherit autotools java

S = "${WORKDIR}/swt-gtk-${PV}.orig"

FILES_${PN} += "${datadir}/java ${libdir}/*.so"

do_compile () {
	oe_runmake
	oe_runmake -f make_linux.mak make_swt make_atk
}

do_install () {
	install -d ${D}${datadir}/java
	install -m 0644 swt-gtk-*.jar ${D}${datadir}/java/
	(cd ${D}${datadir}/java/ && ln -sf swt-gtk-*.jar swt.jar)

	for i in *.so; do
		install -D -m 0755 $i ${D}${libdir}/$i
	done
}
