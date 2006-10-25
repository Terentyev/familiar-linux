# pptp OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Point-to-Point Tunneling Protocol (PPTP) Client"
LICENSE = "GPL"
SECTION = "net"

RDEPENDS = "ppp"

SRC_URI = "${SOURCEFORGE_MIRROR}/pptpclient/${PN}-${PV}.tar.gz"

do_install() {	
	oe_runmake install DESTDIR=${D}
}
