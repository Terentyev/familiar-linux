SECTION = "console/network"
DESCRIPTION = "ppp scripts for establishing gprs connection"
MAINTAINER = "Mika Laitio <lamikr@cc.jyu.fi>"
DEPENDS = "ppp"
RDEPENDS = "ppp"
LICENSE = "GPL"
PR = "r5"

SRC_URI += "file://no_auth_apn_internet_gprs.patch;patch=2"

do_install() {
	install -d ${D}${sysconfdir}/ppp/peers
	install -m 0644 ${S}/etc/ppp/peers/gprs ${D}${sysconfdir}/ppp/peers/gprs
	install -m 0644 ${S}/etc/ppp/pap-secrets ${D}${sysconfdir}/ppp/pap-secrets
	install -d ${D}${sysconfdir}/ppp/chatscripts
	install -m 0644 ${S}/etc/ppp/chatscripts/gprs-connect-chat ${D}${sysconfdir}/ppp/chatscripts/gprs-connect-chat
	install -m 0644 ${S}/etc/ppp/chatscripts/gprs-disconnect-chat ${D}${sysconfdir}/ppp/chatscripts/gprs-disconnect-chat
}
