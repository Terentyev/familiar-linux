include ${PN}.inc

PR = "r1"
    
DEPENDS += "blueprobe"
RDEPENDS += "blueprobe"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/noncore/net/opietooth/applet \
           ${HANDHELDS_CVS};tag=${TAG};module=opie/pics/bluetoothapplet \
	   file://sysconfig-bluetooth.patch;patch=1"
