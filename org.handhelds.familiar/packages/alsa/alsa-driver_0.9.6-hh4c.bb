DESCRIPTION = "Alsa Drivers"
MAINTAINER = "Rene Wagner <rw@handhelds.org>"
SECTION = "base"
LICENSE = "GPL"

EPOCH = "1:"
PR = "r11"

DEPENDS += "fakeroot-native virtual/kernel"

SRC_URI = "ftp://ftp.handhelds.org/packages/alsa-driver/alsa-driver-${PV}.tar.gz \
	file://sound.p.patch;patch=1 \
	file://h5400.patch;patch=1 \
	file://sa11xx.patch;patch=1 \
	file://adriver.h.patch;patch=1"

inherit autotools module update-rc.d

RPROVIDES_${PN} += "${@linux_module_packages('h3600-uda1341 uda1341', d)}"
RCONFLICTS_${PN} += "${@linux_module_packages('h3600-uda1341 uda1341', d)}"
RREPLACES_${PN} += "${@linux_module_packages('h3600-uda1341 uda1341', d)}"

# the same for old style naming...
RPROVIDES_${PN} += "kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37 kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37"
RCONFLICTS_${PN} += "kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37 kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37"
RREPLACES_${PN} += "kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37 kernel-module-h3600-uda1341-2.4.19-rmk6-pxa1-hh37"

INITSCRIPT_NAME = "alsa-driver"
INITSCRIPT_PARAMS = "start 29 S . stop 29 0 6 1 ."

# avoid miscompilation
KERNEL_CC = "${CCACHE}${HOST_PREFIX}gcc${KERNEL_CCSUFFIX} ${HOST_CC_ARCH}"

EXTRA_OECONF = "--with-sequencer=yes \
	--with-isapnp=no \
	--with-oss=yes \
	--with-kernel=${STAGING_KERNEL_DIR} \
	--with-kernel-version=${KERNEL_VERSION}"

PACKAGES =+ "${PN}-midi ${PN}-misc"
FILES_${PN} = "/lib/modules/*/misc/snd* \
	${sysconfdir}/init.d/${INITSCRIPT_NAME}"
#	${sysconfdir}/modutils/*"
midi_modules = "snd-seq-midi-emul.o \
	snd-seq-midi-event.o \
	snd-seq-midi.o \
	snd-seq-virmidi.o \
	snd-seq-oss.o" 
FILES_${PN}-midi = "${@' '.join(map ((lambda x: '/lib/modules/*/misc/%s' % x), bb.data.getVar('midi_modules', d).split()))}"
misc_modules = "snd-gus-synth.o \
	snd-emu8000-synth.o \
	snd-emux-synth.o \
	snd-ainstr-fm.o \
	snd-ainstr-gf1.o \
	snd-ainstr-iw.o \
	snd-ainstr-simple.o"
FILES_${PN}-misc = "${@' '.join(map ((lambda x: '/lib/modules/*/misc/%s' % x), bb.data.getVar('misc_modules', d).split()))}"

# put in-kernel headers first in the include search path.
# without this all configure checks fail
CFLAGS =+ "-I${STAGING_KERNEL_DIR}/include"

do_configure() {

cards=
if egrep "CONFIG_SA1100_H3[168]00=y" "${STAGING_KERNEL_DIR}/.config" ; then
  cards="$cards,sa11xx-uda1341"
  familiar_arch=ipaqsa
fi
if grep "CONFIG_ARCH_H3900=y" "${STAGING_KERNEL_DIR}/.config" ; then
  cards="$cards,pxa-uda1380,h5400-ak4535"
  familiar_arch=ipaqpxa
fi
cards="$cards,bluez-sco,pdaudiocf"

   oe_runconf --with-cards=${cards}
}

autoload_ipaqsa = "snd-sa11xx-uda1341 snd-pcm-oss snd-mixer-oss"
autoload_ipaqpxa = "snd-h5400-ak4535 snd-pxa-uda1380 snd-pcm-oss snd-mixer-oss"

do_install() {

if egrep "CONFIG_SA1100_H3[168]00=y" "${STAGING_KERNEL_DIR}/.config" ; then
  autoload="${autoload_ipaqsa}"
fi
if grep "CONFIG_ARCH_H3900=y" "${STAGING_KERNEL_DIR}/.config" ; then
  autoload="${autoload_ipaqpxa}"
fi

      fakeroot make -k NODEPMOD=yes DESTDIR=${D} install; 

mkdir -p ${D}${sysconfdir}/init.d
cat > ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME} <<EOF
#!/bin/sh

PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin

autoload_modules="$autoload"

case "\$1" in
  start)
	echo -n "Loading alsa modules:"
	for i in \$autoload_modules; do
		modprobe \$i && echo -n " \$i"
	done
	echo "."
	;;
  stop)
	echo -n "Unloading alsa modules:"
	for i in \$autoload_modules; do
		modprobe -r \$i && echo -n " \$i"
	done
	echo "."
	;;
  restart|force-reload)
	echo -n "Unloading alsa modules:"
	for i in \$autoload_modules; do
		modprobe -r \$i && echo -n " \$i"
	done
	echo "."
	echo -n "Loading alsa modules:"
	for i in \$autoload_modules; do
		modprobe \$i && echo -n " \$i"
	done
	echo "."
	;;
  *)
	echo "Usage: \$0 {start|stop|restart|force-reload}" >&2
	exit 1
	;;
esac

exit 0
EOF
chmod 755 ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

