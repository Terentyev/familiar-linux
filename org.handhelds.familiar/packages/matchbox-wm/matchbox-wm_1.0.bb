SECTION = "x11/wm"
DESCRIPTION = "Matchbox window manager"
LICENSE = "GPL"
DEPENDS = "libmatchbox x11 xext xcomposite libxfixes xdamage libxrender startup-notification expat gconf matchbox-common"
RDEPENDS = "matchbox-common"

SRC_URI = "http://projects.o-hand.com/matchbox/sources/matchbox-window-manager/1.0/matchbox-window-manager-${PV}.tar.gz \
	   file://kbdconfig"

S = "${WORKDIR}/matchbox-window-manager-${PV}"

inherit autotools pkgconfig update-alternatives

ALTERNATIVE_NAME = "x-window-manager"
ALTERNATIVE_LINK = "${bindir}/x-window-manager"
ALTERNATIVE_PATH = "${bindir}/matchbox-session"
ALTERNATIVE_PRIORITY = "10"

FILES_${PN} = "${bindir} \
               ${datadir}/matchbox \
               ${sysconfdir}/matchbox \
               ${datadir}/themes/blondie/matchbox \
               ${datadir}/themes/Default/matchbox \
               ${datadir}/themes/MBOpus/matchbox"

EXTRA_OECONF = "--enable-startup-notification --disable-xrm"

do_install_prepend() {
	install ${WORKDIR}/kbdconfig ${S}/data/kbdconfig
}

