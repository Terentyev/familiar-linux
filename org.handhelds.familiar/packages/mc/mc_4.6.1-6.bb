DESCRIPTION = "GNU Midnight Commander - a powerful file manager"
HOMEPAGE = "http://www.ibiblio.org/mc/"
LICENSE = "GPLv2"
SECTION = "utils"

DEPENDS = "ncurses glib-2.0"
RDEPENDS = "ncurses-terminfo"

inherit autotools debian-vampyre

EXTRA_OECONF = "--disable-glibtest --without-x --without-samba \
--without-nfs --without-gpm-mouse --with-screen=ncurses"

do_configure() {
	gnu-configize
	oe_runconf
}

do_install() {
	cd src
	oe_runmake 'DESTDIR=${D}' install
	cd ../syntax
	oe_runmake 'DESTDIR=${D}' install
	cd ../po
	oe_runmake 'DESTDIR=${D}' install
	cd ../vfs
	oe_runmake 'DESTDIR=${D}' install
	cd ..
}
