# java.bbclass OE class file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DEPENDS += "java-wrappers-native \
	classpath \
	fastjar-native \
	jamvm-native \
	jikes-native \
	cp-tools-native \
	zip-native"
RDEPENDS += "classpath"

export JAVA = "jamvm"
export JAVAC = "jikes-classpath"
export JAVAH = "javah-cp -classpath ."
export JAVAP = "false"
export JAR = "fastjar"
export JAVADOC = "true"

export CLASSPATH = ".:${STAGING_DATADIR}/classpath/glibj.zip"

CFLAGS += "-I${STAGING_INCDIR}/classpath"

EXTRA_OEMAKE += "-e JAVA='${JAVA}' JAVAC='${JAVAC}' JAVAH='${JAVAH}' JAR='${JAR}' JAVADOC='${JAVADOC}'"
