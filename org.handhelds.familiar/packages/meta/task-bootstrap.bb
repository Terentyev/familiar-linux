DESCRIPTION = "Core packages required for a basic installation"
MAINTAINER = "Phil Blundell <pb@handhelds.org>"
PR = "r28"

# The BOOTSTRAP_EXTRA_ variables are often manipulated by the
# MACHINE .conf files, so adjust PACKAGE_ARCH accordingly.
PACKAGE_ARCH = "${MACHINE_ARCH}"

ALLOW_EMPTY = 1
PACKAGES = "${PN}"

MODUTILS ?= "24 26"

def bootstrap_modutils_depends(d):
	import bb
	m = bb.data.getVar('MODUTILS', d, 1)
	r = []
	if '24' in m:
		r.append('modutils')
	if '26' in m:
		r.append('module-init-tools')
	return ' '.join(r)

def bootstrap_modutils_rdepends(d):
	import bb
        m = bb.data.getVar('MODUTILS', d, 1)
        r = []
	if '24' in m:
                r.append('modutils-depmod')
        if '26' in m:
                r.append('module-init-tools-depmod')
        return ' '.join(r)

BOOTSTRAP_EXTRA_DEPENDS ?= ""
BOOTSTRAP_EXTRA_RDEPENDS ?= ""
BOOTSTRAP_EXTRA_RRECOMMENDS ?= ""
BOOTSTRAP_EXTRA_RSUGGESTS ?= ""
HOTPLUG ?= "linux-hotplug"

DEPENDS = 'base-files base-passwd-3.5.9 \
	busybox dropbear initscripts modutils netbase \
	sysvinit tinylogin portmap psmisc setserial\
	modutils-initscripts \
	${HOTPLUG} \
	${BOOTSTRAP_EXTRA_DEPENDS} \
	${@bootstrap_modutils_depends(d)}'

RDEPENDS = 'base-files base-passwd busybox \
	initscripts \
	netbase sysvinit sysvinit-pidof tinylogin \
	modutils-initscripts fuser setserial\
	${HOTPLUG} \
	${BOOTSTRAP_EXTRA_RDEPENDS} \
	${@bootstrap_modutils_rdepends(d)}'

RRECOMMENDS = 'dropbear \
	${BOOTSTRAP_EXTRA_RRECOMMENDS}'

RSUGGESTS = 'portmap \
	${BOOTSTRAP_EXTRA_RSUGGESTS}'

LICENSE = MIT
