# pocketsphinx OE build file
# Copyright (C) 2006, Rene Wagner. All Rights Reserved
# Released under the MIT license (see org.handhelds.familiar/COPYING.MIT)

DESCRIPTION = "Sphinx speech recognition system for handhelds"
LICENSE = "BSD"
PRIORITY = "optional"
SECTION = "sound"

SRC_URI = "${SOURCEFORGE_MIRROR}/cmusphinx/${PN}-${PV}.tar.bz2"

inherit autotools

CFLAGS =+ "-I${STAGING_INCDIR}/sphinxbase"
CPPFLAGS =+ "-I${STAGING_INCDIR}/sphinxbase"

PACKAGES =+ "lib${PN} ${PN}-model-hmm ${PN}-model-lm"
FILES_lib${PN} = "${libdir}/*.so.*"
FILES_${PN}-model-hmm = "${datadir}/${PN}/model/hmm"
FILES_${PN}-model-lm = "${datadir}/${PN}/model/lm"
RRECOMMENDS_${PN} = "${PN}-model-hmm ${PN}-model-lm"
