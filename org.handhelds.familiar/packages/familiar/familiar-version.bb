PACKAGE_ARCH = "all"

def get_sanitized_version(s):

	max_version_component = "99"
	pre_separators = ["-rc", "-pre"]

	ver = s

	for sep in pre_separators:
		if not sep in s:
			continue

		version = s.split(sep)[0][1:]
		vcomps = version.split(".")
		vcomps.reverse()

		vcomps_new = []
		done = False
		for i in vcomps:
			if done:
				vcomps_new.insert(0, i)
				continue
			if int(i) < 1:
				vcomps_new.insert(0, max_version_component)
				continue
			vcomps_new.insert(0, "%i" % (int(i) - 1))
			done = True

		ver = "v" + ".".join(vcomps_new) + "+" +  s.replace("-", "")
	
	return ver

PV = "${@get_sanitized_version(bb.data.getVar('DISTRO_VERSION', d, 1))}"

do_install() {
	mkdir -p ${D}${sysconfdir}
	echo "Familiar ${DISTRO_VERSION}" > ${D}${sysconfdir}/familiar-version
}
