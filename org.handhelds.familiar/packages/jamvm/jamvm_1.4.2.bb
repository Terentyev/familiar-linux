# jamvm OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "A compact Java Virtual Machine which conforms to the JVM specification version 2."
HOMEPAGE = "http://jamvm.sourceforge.net/"
LICENSE = "GPL"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "interpreters"
PR = "r1"

DEPENDS = "zlib classpath"
RDEPENDS = "classpath (>= 0.20) classpath-common (>= 0.20)"

SRC_URI = "${SOURCEFORGE_MIRROR}/jamvm/jamvm-${PV}.tar.gz \
           file://jamvm-1.3.1-size-defaults.patch;patch=1 \
	   file://debian-jni.patch;patch=1"

# This uses 32 bit arm, so force the instruction set to arm, not thumb
ARM_INSTRUCTION_SET = "arm"

inherit autotools update-alternatives

EXTRA_OECONF = "--with-classpath-install-dir=${prefix}"
CFLAGS += "-DDEFAULT_MAX_HEAP=16*MB"

PROVIDES = "virtual/java"
ALTERNATIVE_NAME = "java"
ALTERNATIVE_PATH = "${bindir}/jamvm"
ALTERNATIVE_PRIORITY = "10"
