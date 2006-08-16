include ${PN}.inc

PR = "r3"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/noncore/net/opietooth/blue-pin \
	   file://bluepin"

FILES_${PN} += " /bin/bluepin"

do_install() {
	install -d ${D}/bin
	install -m 0700 ${WORKDIR}/bluepin ${D}/bin
}
