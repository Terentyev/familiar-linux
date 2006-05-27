DESCRIPTION = "Sync daemon for GPE and OpenSync"
LICENSE = "GPL"
MAINTAINER = "Koen Kooi <koen@handhelds.org>"

PV = "0.0+cvs-${CVSDATE}"
PR = "r1"

DEPENDS = "libgpevtype sqlite libmimedir glib-2.0"

SRC_URI = "${HANDHELDS_CVS};module=gpe/base/gpesyncd"
S = "${WORKDIR}/${PN}"

inherit autotools

FILES_${PN} +=	"${datadir}/gpe"

