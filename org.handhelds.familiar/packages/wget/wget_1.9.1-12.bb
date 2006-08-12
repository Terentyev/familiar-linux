DESCRIPTION = "A console URL download utility featuring HTTP, FTP, and more."
SECTION = "console/network"
LICENSE = "GPL"

inherit autotools gettext debian-vampyre update-alternatives

SRC_URI += "file://m4macros.patch;patch=1 \
	    file://autotools.patch;patch=1"

EXTRA_OECONF = "--enable-ipv6"

# The unslung kernel does not support ipv6
EXTRA_OECONF_unslung = ""

do_configure () {
	if [ ! -e acinclude.m4 ]; then
		mv aclocal.m4 acinclude.m4
	fi
	rm -f libtool.m4
	autotools_do_configure
}

do_install () {
	autotools_do_install
	mv ${D}${bindir}/wget ${D}${bindir}/wget.${PN}
}

ALTERNATIVE_NAME = "wget"
ALTERNATIVE_PATH = "${bindir}/wget.${PN}"
ALTERNATIVE_PRIORITY = "100"
