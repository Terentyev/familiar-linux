LICENSE = "GPLv2"
DEPENDS = "zlib bzip2"
DEPENDS += "readline"

inherit autotools gettext debian-vampyre

EXTRA_OECONF = "--disable-ldap \
		--with-zlib=${STAGING_LIBDIR}/.. \
		--with-bzip2=${STAGING_LIBDIR}/.. \
		--disable-selinux-support"
EXTRA_OECONF += "--with-readline=${STAGING_LIBDIR}/.."
#   --without-readline      do not support fancy command line editing

do_install () {
	autotools_do_install
	install -d ${D}${docdir}/${PN}
	mv ${D}${datadir}/${PN}/* ${D}/${docdir}/${PN}/ || :
	mv ${D}${prefix}/doc/* ${D}/${docdir}/${PN}/ || :
}

