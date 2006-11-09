# cp-tools-native OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

include cp-tools_${PV}.bb
inherit native

DEPENDS="autoconf-native automake-native libtool-native gnu-config-native quilt-native \
         java-wrappers-native classpath fastjar-native jamvm-native jikes-native zip-native"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/cp-tools"

do_stage() {

	# stage jars
	mkdir -p ${STAGING_DATADIR}/java

	install -m 0644 cptools-0.00-cvs.jar ${STAGING_DATADIR}/java/cptools-${PV}.jar
	(cd ${STAGING_DATADIR}/java && ln -sf cptools-${PV}.jar cptools-0.00-cvs.jar)
	(cd ${STAGING_DATADIR}/java && ln -sf cptools-${PV}.jar cptools.jar)

	install -m 0644 ${WORKDIR}/bytecode.jar ${STAGING_DATADIR}/java/

	# stage wrapper scripts
	mkdir -p ${STAGING_BINDIR}
	for i in ${binprograms}; do
		sed -e 's,java,java -cp .:${STAGING_DATADIR}/java/bytecode.jar:${STAGING_DATADIR}/java/cptools.jar,' bin/$i > bin/$i-cp
		install -m 0755 bin/$i-cp ${STAGING_BINDIR}/$i-cp
	done
}

