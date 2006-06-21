inherit gpe

DEPENDS = "virtual/xserver xtst xau xpm"
SECTION = "gpe"
LICENSE = "GPL"
DESCRIPTION = "A small utility for binding commands to a hot key (GPE version)"
PR = "r2"

SRC_URI += " file://keylaunchrc"

do_install_prepend () {
	install ${WORKDIR}/keylaunchrc ${S}/keylaunchrc
}

export CVSBUILD="no"
