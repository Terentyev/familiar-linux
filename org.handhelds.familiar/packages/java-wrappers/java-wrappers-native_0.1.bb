# java-wrappers-native OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Wrapper scripts to select Java related tools by environment variables"
LICENSE = "GPL"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"

inherit native

SRC_URI = "file://java \
	file://javac \
	file://javah \
	file://javap \
	file://jar \
	file://javadoc"

do_stage () {
	install -d ${STAGING_BINDIR}
	for i in java javac javah javap jar javadoc; do
		install -m 0755 ${WORKDIR}/$i ${STAGING_BINDIR}/
	done
}
