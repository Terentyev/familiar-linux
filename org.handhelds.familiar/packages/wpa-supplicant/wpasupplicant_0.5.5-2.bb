# wpasupplicant OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Client support for WPA and WPA2 (IEEE 802.11i)"
SECTION = "net"
LICENSE = "GPL"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
PR = "r1"

DEPENDS = "gnutls"
RDEPENDS_${PN} = "busybox (>= 1.00-r37)"

RCONFLICTS_${PN} = "wpa-supplicant wpa-supplicant-nossl"
RPROVIDES_${PN} = "wpa-supplicant wpa-supplicant-nossl"
RREPLACES_${PN} = "wpa-supplicant wpa-supplicant-nossl"

inherit debian-vampyre

SRC_URI += "file://tls-gnutls-api-breakage.patch;patch=1 \
	file://scripts-standard-sh.patch;patch=1 \
	file://scripts-no-quiet.patch;patch=1 \
	file://scripts-no-lsb.patch;patch=1 \
	file://defconfig"
S = "${WORKDIR}/wpa_supplicant-${DPV}"

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig  .config
}

do_compile () {
	make
}

base_sbin_apps = "wpa_supplicant wpa_cli"
bin_apps = "wpa_passphrase"
network_dirs = "if-down.d if-post-down.d if-pre-up.d if-up.d"
man5 = "wpa_supplicant.conf.5"
man8 = "wpa_background.8 wpa_cli.8 wpa_passphrase.8 wpa_supplicant.8"
debian_man8 = "wpa_action.8"
gz_docs = "ChangeLog README"
examples = "ieee8021x.conf plaintext.conf wep.conf wpa-psk-tkip.conf wpa2-eap-ccmp.conf"
debian_examples = "wpa_supplicant.conf.template wpa_supplicant.init-daemon"

do_install () {
	# binaries
	install -d ${D}${base_sbindir}
	for i in ${base_sbin_apps}; do
		install -m 0755 $i ${D}${base_sbindir}
	done
	install -m 0755 debian/wpa_action.sh ${D}${base_sbindir}/wpa_action

	install -d ${D}${bindir}
	for i in ${bin_apps}; do
		install -m 0755 $i ${D}${bindir}
	done

	# ifupdown scripts
	install -d ${D}${sysconfdir}/wpa_supplicant
	for i in functions.sh ifupdown.sh; do
		install -m 0755 debian/$i ${D}${sysconfdir}/wpa_supplicant
	done
	for i in ${network_dirs}; do
		install -d ${D}${sysconfdir}/network/$i
		(cd ${D}${sysconfdir}/network/$i && ln -s ../../wpa_supplicant/ifupdown.sh wpasupplicant)
	done

	# init script (only for manual stop|reload)
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 debian/wpasupplicant.wpa-ifupdown.init ${D}${sysconfdir}/init.d/wpa-ifupdown

	# man pages
	install -d ${D}${mandir}
	install -d ${D}${mandir}/man5
	for i in ${man5}; do
		install -m 0644 doc/docbook/$i ${D}${mandir}/man5
		gzip -9 ${D}${mandir}/man5/$i
	done
	install -d ${D}${mandir}/man8
	for i in ${man8}; do
		install -m 0644 doc/docbook/$i ${D}${mandir}/man8
		gzip -9 ${D}${mandir}/man8/$i
	done
	for i in ${debian_man8}; do
		install -m 0644 debian/$i ${D}${mandir}/man8
		gzip -9 ${D}${mandir}/man8/$i
	done

	# docs and examples
	install -d ${D}${docdir}/wpasupplicant
	for i in ${gz_docs}; do
		install -m 0644 $i ${D}${docdir}/wpasupplicant
		gzip -9 ${D}${docdir}/wpasupplicant/$i
	done
	install -d ${D}${docdir}/wpasupplicant/examples
	for i in ${examples}; do
		install -m 0644 examples/$i ${D}${docdir}/wpasupplicant/examples
	done
	for i in ${debian_examples}; do
		install -m 0644 debian/$i ${D}${docdir}/wpasupplicant/examples
	done
}
