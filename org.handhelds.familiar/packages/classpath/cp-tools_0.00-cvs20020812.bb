# cp-tools OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "GNU Classpath tools (javah, javap, etc.)"
HOMEPAGE = "http://www.gnu.org/software/classpath/cp-tools/"
LICENSE = "GPLv2"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "utils"

inherit java

FIXEDCVSDATE = "${@bb.data.getVar('PV', d, 1).split('cvs')[-1]}"
SRC_URI = "cvs://anoncvs@savannah.gnu.org/cvsroot/classpath;module=cp-tools;method=ext;date=${FIXEDCVSDATE}"
S = "${WORKDIR}/cp-tools"

# bypassing the original build system. only compiling pure java code.
do_configure () {
	true
}

do_compile () {
	echo $JAVAC `find . -name '*.java' | grep -v tools/rmi`
	$JAVAC -cp . `find . -name '*.java'`
	echo $JAR cfm ../cp-tools.jar `find . -name '*.class'`
	$JAR cfm ../cp-tools.jar `find . -name '*.class'`
}

do_install () {
	install -d ${D}${datadir}
	install -d ${D}${datadir}/java

	install -m 0644 cp-tools.jar ${D}${datadir}/java/cp-tools-${PV}.jar
	(cd ${D}${datadir}/java && ln -sf cp-tools-${PV}.jar cp-tools.jar)

	install -d ${D}${bindir}
	for i in javah javap serialver; do
		sed -e 's:java:java -cp ${datadir}/java/cp-tools.jar:' bin/$i > bin/$i-cp
		install -m 0755 bin/$i-cp ${D}${bindir}/$i-cp
	done
}

pkg_portinst () {
	for i in javah javap serialver; do
		update-alternatives --install ${bindir}/$i $i ${bindir}/$i-cp 350
	done
}

pkg_postrm_append () {
	for i in javah javap serialver; do
		update-alternatives --remove $i ${bindir}/$i-cp
	done
}

