# classpath OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "GNU Classpath standard Java libraries"
HOMEPAGE = "http://www.gnu.org/software/classpath/"
LICENSE = "Classpath"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "libs"
PR = "r1"

DEPENDS = "glib-2.0 gtk+ libart-lgpl pango xtst jikes-native zip-native"
RDEPENDS_${PN} = "${PN}-common (>= ${PV})"
RDEPENDS_jikes-${PN} = "${PN} jikes"

SRC_URI = "${GNU_MIRROR}/classpath/classpath-${PV}.tar.gz \
           file://disable-automake-checks.patch;patch=1 \
	   file://jikes-classpath.sh"

inherit autotools

EXTRA_OECONF = "--with-jikes --disable-alsa"

PACKAGES += " ${PN}-common ${PN}-examples jikes-${PN}"

FILES_${PN} = "${libdir}/${PN}"
FILES_${PN}-common = "${datadir}/${PN}/glibj.zip"
FILES_${PN}-examples = "${datadir}/${PN}/examples"
FILES_jikes-${PN} = "${bindir}"

do_stage() {
	# stage JNI headers
	install -d ${STAGING_INCDIR}/classpath
	install -m 0644 include/jni* ${STAGING_INCDIR}/classpath/
	
	# stage glibj.zip (containing all class files)
	install -d ${STAGING_DATADIR}/classpath
	install -m 0644 lib/glibj.zip ${STAGING_DATADIR}/classpath/

	# stage jikes-classpath wrapper script
	install -d ${STAGING_BINDIR}
	# just to get the permissions right
	install -m 0755 ${WORKDIR}/jikes-classpath.sh ${STAGING_BINDIR}/jikes-classpath
	sed -e "s:DATADIR:${STAGING_DATADIR}:" ${WORKDIR}/jikes-classpath.sh > ${STAGING_BINDIR}/jikes-classpath
}

do_install() {
	autotools_do_install
	mv ${D}${libdir}/security ${D}${libdir}/${PN}

	# install jikes-classpath wrapper script
	install -d ${D}${bindir}
	# just to get the permissions right
	install -m 0755 ${WORKDIR}/jikes-classpath.sh ${D}${bindir}/jikes-classpath
	sed -e "s:DATADIR:${datadir}:" ${WORKDIR}/jikes-classpath.sh > ${D}${bindir}/jikes-classpath
}

pkg_postinst_jikes-${PN} () {
	update-alternatives --install ${bindir}/javac javac ${bindir}/jikes-${PN} 10
}

pkg_postrm_jikes-${PN} () {
	update-alternatives --remove javac ${bindir}/jikes-${PN}
}
