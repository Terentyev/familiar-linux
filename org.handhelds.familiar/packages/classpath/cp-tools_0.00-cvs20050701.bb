# cp-tools OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "GNU Classpath tools (javah, javap, etc.)"
HOMEPAGE = "http://www.gnu.org/software/classpath/cp-tools/"
LICENSE = "GPLv2"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "utils"

inherit autotools java

FIXEDCVSDATE = "${@bb.data.getVar('PV', d, 1).split('cvs')[-1]}"
SRC_URI = "cvs://anonymous@cvs.sv.gnu.org/cvsroot/classpath;module=cp-tools;date=${FIXEDCVSDATE} \
	file://bytecodejar-workaround.patch;patch=1 \
	file://destdir.patch;patch=1 \
	${GNU_MIRROR}/kawa/kawa-1.7.tar.gz"
S = "${WORKDIR}/cp-tools"

export CLASSPATH = ".:${STAGING_DATADIR}/classpath/glibj.zip:${WORKDIR}/bytecode.jar"

EXTRA_OECONF = "--disable-native --with-gnu-bytecode-jar=${WORKDIR}/bytecode.jar"

FILES_${PN} += "${datadir}/java"

do_configure () {
	cd ${WORKDIR}/kawa-1.7
	javac gnu/bytecode/*.java
	zip bytecode.jar gnu/bytecode/*.class || die
	mv bytecode.jar ..

	cd ${S}
	autotools_do_configure
}

binprograms="currencygen \
	javah \
	javap \
	localegen \
	native2ascii \
	rmic \
	rmiregistry \
	serialver"

do_install () {
	install -d ${D}${datadir}
	install -d ${D}${datadir}/java

	install -m 0644 cptools-0.00-cvs.jar ${D}${datadir}/java/cptools-${PV}.jar
	(cd ${D}${datadir}/java && ln -sf cptools-${PV}.jar cptools-0.00-cvs.jar)
	(cd ${D}${datadir}/java && ln -sf cptools-${PV}.jar cptools.jar)

	install -m 0644 ${WORKDIR}/bytecode.jar ${D}${datadir}/java/

	install -d ${D}${bindir}
	for i in ${binprograms}; do
		sed -e 's,java,java -cp .:${datadir}/java/bytecode.jar:${datadir}/java/cptools.jar,' bin/$i > bin/$i-cp
		install -m 0755 bin/$i-cp ${D}${bindir}/$i-cp
	done
}

pkg_postinst () {
	for i in ${binprograms}; do
		update-alternatives --install ${bindir}/$i $i ${bindir}/$i-cp 350
	done
}

pkg_postrm_append () {
	for i in ${binprograms}; do
		update-alternatives --remove $i ${bindir}/$i-cp
	done
}

