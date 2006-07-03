include mtd-utils_${PV}.bb
inherit native

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ipaq-pxa270 = "0"
DEPENDS = "zlib-native"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/mtd-utils"

do_stage () {
	for binary in ${mtd_utils}; do
		install -m 0755 util/$binary ${STAGING_BINDIR}/
	done
}
