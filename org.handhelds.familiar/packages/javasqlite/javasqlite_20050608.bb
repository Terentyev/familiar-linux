# javasqlite OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "SQLite Java Wrapper/JDBC Driver"
HOMEPAGE = "http://www.ch-werner.de/javasqlite/"
LICENSE = "unknown"
PRIORITY = "optional"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "libs"
PR = "r1"

DEPENDS = "sqlite"

SRC_URI = "http://www.ch-werner.de/javasqlite/javasqlite-${PV}.tar.gz \
	file://buildsystem-fixes.patch;patch=1"

inherit autotools java

EXTRA_OECONF = "--with-sqlite=${STAGING_INCDIR} \
	--with-sqlite3=${STAGING_INCDIR}"

FILES_${PN} += "${datadir}/java ${libdir}/*.so"

