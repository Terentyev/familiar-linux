DESCRIPTION =	"Gomunicator is a GSM Voice and SMS application for GPE"
HOMEPAGE = "http://www.linuxdevelopment.org/projects.html"
LICENSE = "GPLv2"
AUTHOR = "Robert Woerle"
MAINTAINER = "Koen Kooi <koen@handhelds.org>"
DEPENDS = "libgpewidget gtk+ glib-2.0 alsa-lib"
#Remove the dash below when 0.1.3 changes in PV
PV = "0.1.3+cvs-${CVSDATE}"

inherit autotools pkgconfig

SRC_URI = "cvs://anonymous@xanadux.cvs.sourceforge.net/cvsroot/xanadux;module=gomunicator"
S = "${WORKDIR}/${PN}"
