PR          = "r2"
LICENSE     = "GPL"
DEPENDS     = "libgpewidget iptables virtual/kernel"
RDEPENDS    = "gpe-su iptables"
RRECOMMENDS = "kernel-module-ipt-state"
SECTION     = "gpe"
MAINTAINER  = "Florian Boor <florian.boor@kernelconcepts.de>"

DESCRIPTION = "GPE network security tool"

inherit gpe

SRC_URI += "file://ipshield"

do_install_append () {
	chmod 0755 ${D}${bindir}/gpe-shield
	sed -i -e 's:Exec=gpe-shield:Exec=gpe-su -c "${bindir}/gpe-shield > /dev/null":' ${D}${datadir}/applications/${PN}.desktop
	install -m 0755 ${WORKDIR}/ipshield ${D}${sysconfdir}/init.d
}
