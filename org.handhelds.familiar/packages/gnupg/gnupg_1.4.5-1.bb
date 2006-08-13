DESCRIPTION = "GNU privacy guard - a free PGP replacement"
HOMEPAGE = "http://www.gnupg.org/"
SECTION = "console/utils"
LICENSE = "GPLv2"

DEPENDS = "zlib bzip2 readline"

inherit autotools gettext debian-vampyre

SRC_URI += "file://15_free_caps.dpatch;patch=1 \
	file://16_min_privileges.dpatch;patch=1 \
	file://24_gpgv_manpage_cleanup.dpatch;patch=1;pnum=0 \
	file://25_de.po_fixes.dpatch;patch=1 \
	file://tar-ustar-1.4.3.m4"

EXTRA_OECONF = "--disable-ldap \
		--with-zlib=${STAGING_LIBDIR}/.. \
		--with-bzip2=${STAGING_LIBDIR}/.. \
		--disable-selinux-support"
EXTRA_OECONF += "--with-readline=${STAGING_LIBDIR}/.. \
		 --without-libcurl \
		 --without-libusb"

do_configure () {
	cp ${WORKDIR}/tar-ustar-1.4.3.m4 ${S}/m4/tar-ustar.m4
	autotools_do_configure
}

do_install () {
	autotools_do_install
	install -d ${D}${docdir}/${PN}
	mv ${D}${datadir}/${PN}/* ${D}/${docdir}/${PN}/ || :
	mv ${D}${prefix}/doc/* ${D}/${docdir}/${PN}/ || :
}

