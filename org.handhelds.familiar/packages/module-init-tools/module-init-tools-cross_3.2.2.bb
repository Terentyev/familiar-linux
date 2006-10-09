include module-init-tools_${PV}.bb
PR = "r2"

inherit cross
DEFAULT_PREFERENCE = "0"
PROVIDES += "virtual/${TARGET_PREFIX}depmod virtual/${TARGET_PREFIX}depmod-2.6"

DEPENDS += "virtual/${TARGET_PREFIX}depmod-2.4"
SRC_URI += "file://cross.patch;patch=1"

EXTRA_OECONF_append = " --program-prefix=${TARGET_PREFIX}"

do_stage () {
        oe_runmake install
}

do_install () {
        :
}
