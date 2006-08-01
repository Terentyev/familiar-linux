# debian-vampyre.bbclass - the supermarket thing
#
# Copyright (C) 2006, Rene Wagner
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)
#

PN = "${@bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[0] or 'defaultpkgname'}"
PV = "${@'-'.join((bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[1] or '1.0').split('-')[:-1])}"
PR = "${@(bb.parse.BBHandler.vars_from_file(bb.data.getVar('FILE',d),d)[1] or '1').split('-')[-1]}"

DEBIAN_ARCHIVE ?= "main"
DEBIAN_BASE_URI = "${DEBIAN_MIRROR}/${DEBIAN_ARCHIVE}/${@bb.data.getVar('PN', d, 1)[0]}/${PN}"
SRC_URI_prepend = "${DEBIAN_BASE_URI}/${PN}_${PV}.orig.tar.gz \
                   ${DEBIAN_BASE_URI}/${PN}_${PV}-${PR}.diff.gz;patch=1 "

python do_dpatch () {
	plist = (base_read_file("%s/debian/patches/00list" % bb.data.getVar("S", d, 1)) or "").split()
	for p in plist:
		bb.note("Applying patch '%s'" % p)
		bb.data.setVar("do_patchcmd", bb.data.getVar("PATCHCMD", d, 1) % (1, p, "${S}/debian/patches/%s.dpatch" % p), d)
		bb.data.setVarFlag("do_patchcmd", "func", 1, d)
		bb.data.setVarFlag("do_patchcmd", "dirs", "${WORKDIR} ${S}", d)
		bb.build.exec_func("do_patchcmd", d)
}

python patch_applied() {
	if bb.data.getVar("PATCH_APPLIED_NUM", d, 1) != 0:
		return

	import os
	import os.path
	
	dpatch = False
	try:
		os.stat(os.path.join(bb.data.getVar("S", d, 1), "debian", "patches", "00list"))
		dpatch = True
	except Exception, e:
		pass
	
	if dpatch:
		bb.event.fire(bb.build.TaskStarted("do_dpatch", d))
		bb.build.exec_func("do_dpatch", d)
		bb.event.fire(bb.build.TaskSucceeded("do_dpatch", d))
}

def parse_control(bbvar, field, d):
	import bb
	import re
	
	readable = False
	try:
		control = (base_read_file("%s/debian/control" % bb.data.getVar("S", d, 1)) or "").split("\n")
		readable = True
	except Exception, e:
		pass

	if readable:
		pkg = None
		r = re.compile("([a-zA-Z]*): (.*)$")

		fields = {}
		for line in control:
			m = r.match(line)
			if m:
				if m.group(1) == "Package":
					pkg = m.group(2)
				if not pkg or pkg == bb.data.getVar("PN", d, 1):
					fields[m.group(1)] = m.group(2)
		if fields.has_key(field):
			return fields[field]

	return ""

def flatten(s):
	return ' '.join(s.split(','))

DESCRIPTION = "${@parse_control('DESCRIPTION', 'Description', d)}"
SECTION = "${@parse_control('SECTION', 'Section', d)}"
PRIORITY = "${@parse_control('PRIORITY', 'Priority', d)}"
RCONFLICTS_${PN} = "${@flatten(parse_control('RCONFLICTS', 'Conflicts', d))}"
RREPLACES_${PN} = "${@flatten(parse_control('RREPLACES', 'Replaces', d))}"
RPROVIDES_${PN} = "${@flatten(parse_control('RPROVIDES', 'Provides', d))}"
	
do_install_append () {
	mkdir -p ${D}${datadir}/doc/${PN}
	rm -f ${D}${datadir}/doc/${PN}/changelog.Debian*
	install -m 0644 debian/changelog ${D}${datadir}/doc/${PN}/changelog.Debian
	gzip -9 ${D}${datadir}/doc/${PN}/changelog.Debian
	[ -f debian/README.Debian ] && install -m 0644 debian/README.Debian ${D}${datadir}/doc/${PN}/
}
