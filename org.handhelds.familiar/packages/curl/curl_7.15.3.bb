DESCRIPTION = "Command line tool and library for client-side URL transfers."
LICENSE = "MIT"
DEPENDS = "zlib"
SECTION = "console/network"

SRC_URI = "http://curl.haxx.se/download/curl-${PV}.tar.bz2 \
           file://dont-touch-ld-library-path.patch;patch=1"
S = "${WORKDIR}/curl-${PV}"

inherit autotools pkgconfig binconfig

EXTRA_OECONF = "--with-zlib=${STAGING_LIBDIR}/../ \
		--without-ssl \
		--with-gnutls \
		--with-random=/dev/urandom \
		--without-idn"

do_stage () {
	install -d ${STAGING_INCDIR}/curl
	install -m 0644 ${S}/include/curl/*.h ${STAGING_INCDIR}/curl/
	oe_libinstall -so -a -C lib libcurl ${STAGING_LIBDIR}
}

PACKAGES = "curl curl-doc libcurl libcurl-dev libcurl-doc"
FILES_${PN} = "${bindir}/curl"
FILES_${PN}-doc = "${mandir}/man1/curl.1"
FILES_lib${PN} = "${libdir}/lib*.so.* \
                  ${datadir}/curl/curl-ca-bundle.crt"
FILES_lib${PN}-dev = "${includedir} \
                      ${libdir}/lib*.so \
                      ${libdir}/lib*.a \
                      ${libdir}/lib*.la \
                      ${libdir}/pkgconfig \
                      ${datadir}/aclocal \
                      ${bindir}/*-config"
FILES_lib${PN}-doc = "${mandir}/man3 \
                      ${mandir}/man1/curl-config.1"

