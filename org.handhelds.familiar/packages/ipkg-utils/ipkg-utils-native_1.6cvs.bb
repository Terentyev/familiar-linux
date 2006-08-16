SECTION = "base"
include ipkg-utils_${PV}.bb
PR = "r5"
inherit native
DEPENDS = ""

SRC_URI += "file://dangling-symlinks.patch;patch=1"

do_stage() {
        for i in ${INSTALL}; do
                install -m 0755 $i ${STAGING_BINDIR}
        done
}
