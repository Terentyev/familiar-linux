DESCRIPTION = "Device-buttons settings dialog"
SECTION = "opie/settings"
PRIORITY = "optional"
MAINTAINER = "Team Opie <opie@handhelds.org>"
LICENSE = "GPL"
RDEPENDS = "opie-button-settings-pics"
APPNAME = "buttonsettings"

S = "${WORKDIR}/button"

inherit opie

do_install() {
        install -d ${D}${palmtopdir}/pics/${APPNAME}/
	install -d ${D}${palmtopdir}/pics/devicebuttons/
        install -m 0644 ${WORKDIR}/pics/${APPNAME}/*.png ${D}${palmtopdir}/pics/${APPNAME}/
	install -m 0644 ${WORKDIR}/pics/devicebuttons/*.png ${D}${palmtopdir}/pics/devicebuttons/
}

PACKAGES =+ "opie-button-settings-pics"
FILES_${PN} = "${palmtopdir}/apps \
	${palmtopdir}/bin \
	${palmtopdir}/plugins \
	${palmtopdir}/pics/${APPNAME}"
PACKAGE_ARCH_opie-button-settings-pics = "${MACHINE_ARCH}"
FILES_opie-button-settings-pics = "${palmtopdir}/pics/devicebuttons/z*" 
FILES_opie-button-settings-pics_h3600 = "${palmtopdir}/pics/devicebuttons/ipaq*"
FILES_opie-button-settings-pics_h3900 = "${palmtopdir}/pics/devicebuttons/ipaq*"
FILES_opie-button-settings-pics_h1910 = "${palmtopdir}/pics/devicebuttons/ipaq*"
FILES_opie-button-settings-pics_h2200 = "${palmtopdir}/pics/devicebuttons/ipaq*"
FILES_opie-button-settings-pics_ipaq-pxa270 = "${palmtopdir}/pics/devicebuttons/ipaq*"
FILES_opie-button-settings-pics_simpad = "${palmtopdir}/pics/devicebuttons/simpad*"
