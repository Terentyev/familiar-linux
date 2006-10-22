SECTION = "base"
LICENSE = "GPL"

PARALLEL_INSTALL_REPLACE_VERSIONS = "2.4.19-rmk6-pxa1-hh37 2.4.19-rmk6-pxa1-hh41.1"

SRC_URI = "cvs://anonymous@cvs.berlios.de/cvsroot/at76c503a;module=at76c503a;tag=version_${@bb.data.getVar('PV', d, 1).replace('.', '_')} \
	   file://makefile.cc.patch;patch=1 \
	   file://ipaq-compat.patch;patch=1"
S = "${WORKDIR}/at76c503a"

inherit module

MODULES = "at76c503 at76_usbdfu at76c503-i3861 at76c503-rfmd at76c503-rfmd-acc \
	at76c505-rfmd at76c503-i3863 at76c505-rfmd2958"

do_install() {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/
	for i in ${MODULES}; do
		install -m 0644 $i${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/
	done
	if [ "${MACHINE}" = "h3900" ]; then
		install -d ${D}${sysconfdir}/modutils
		echo "at76c503-rfmd" > ${D}${sysconfdir}/modutils/at76c503-rfmd
	fi
}
