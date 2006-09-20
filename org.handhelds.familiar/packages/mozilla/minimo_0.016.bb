DESCRIPTION = "A small, simple, powerful, innovative, web browser for mobile devices"
HOMEPAGE = "http://www.mozilla.org/projects/minimo/"
MAINTAINER = "Rene Wagner <rw@handhelds.org>, Paul Parsons <lost.distance@yahoo.com>"
SECTION = "x11"
PRIORITY = "optional"
LICENSE = "MPL/LGPL/GPL"
EPOCH = "1:"

CVSDATE="20060720"

SRC_URI = "cvs://anonymous@cvs-mirror.mozilla.org/cvsroot;module=mozilla \
	   file://xptcstubs.patch;patch=1 \
	   file://no-x86-asm.patch;patch=1 \
	   file://no-xmb.patch;patch=1 \
	   file://host_ldflags_fix.patch;patch=1 \
	   file://lost-distance.patch;patch=1 \
	   file://minimo-components-ssr-only.patch;patch=1 \
	   file://minimo.png \
	   file://minimo.desktop"
S = "${WORKDIR}/mozilla"

inherit mozilla

EXTRA_OECONF += "--enable-application=minimo --disable-native-uconv"
export MOZ_CO_PROJECT = "minimo"
export MOZ_CO_TAG = "MOZILLA_1_8_BRANCH"

include mozilla-cvs.inc

do_configure() {
	(
		set -e
		for cg in `find ${S} -name config.guess`; do
			install -m 0755 \
			${STAGING_BINDIR}/../share/gnu-config/config.guess \
			${STAGING_BINDIR}/../share/gnu-config/config.sub \
			`dirname $cg`/
		done
	)
	cd ${S}
	oe_runmake -f client.mk CONFIGURE_ARGS="${EXTRA_OECONF}" configure
}

do_compile () {
	oe_runmake -f client.mk build
}

mozdir = "${libdir}/mozilla-minimo"
FILES_${PN} += "${mozdir}"

do_install () {
	./minimo/config/linux_package.sh ${S} ${S}/minimo/config
	
	mkdir -p ${D}${mozdir}
	cp -rL $MOZ_OBJDIR/dist/minimo/* ${D}${mozdir}/
	
	mkdir -p ${D}${datadir}/applications
	install -m 0644 ${WORKDIR}/minimo.desktop ${D}${datadir}/applications/

	mkdir -p ${D}${datadir}/pixmaps
	install -m 0644 ${WORKDIR}/minimo.png ${D}${datadir}/pixmaps/

	mkdir -p ${D}${bindir}
	cat > ${D}${bindir}/minimo << EOF
#!/bin/sh
export MOZILLA_FIVE_HOME=${mozdir}
export LD_LIBRARY_PATH=\$MOZILLA_FIVE_HOME
exec \$MOZILLA_FIVE_HOME/minimo "\$@"
EOF
	chmod 755 ${D}${bindir}/minimo
}
