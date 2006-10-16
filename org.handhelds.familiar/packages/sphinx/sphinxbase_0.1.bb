# sphinxbase OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Common libraries and utilities of the Sphinx family of speech recognition systems"
LICENSE = "BSD"
PRIORITY = "optional"
SECTION = "libs"

SRC_URI = "${SOURCEFORGE_MIRROR}/cmusphinx/${PN}-${PV}.tar.bz2"

inherit autotools

EXTRA_OECONF_append_arm = " --enable-fixed"

do_stage() {
	autotools_stage_all
}

PACKAGES = "${PN}-dev ${PN}-utils"
FILES_${PN}-utils = "${bindir}"

python populate_packages_prepend () {
	sphinx_libdir = bb.data.expand('${libdir}', d)

	do_split_packages(d, sphinx_libdir, '^libsphinx(.*?)\.so.*$', 'libsphinx%s', bb.data.expand('${DESCRIPTION}', d) + ' - lib%s', aux_files_pattern_verbatim = sphinx_libdir + '/libsphinx%s.*')
	do_split_packages(d, sphinx_libdir, '^libsphinx(.*?)\.l?a.*$', 'libsphinx%s-dev', bb.data.expand('${DESCRIPTION}', d) + ' - lib%s')
}
