DISTRO_FEEDS_IN_feed += "x11"
DISTRO_FEEDS_IN_universe += "x11"

PR = "r21"

export IMAGE_BASENAME = "gpe-image"

GUI_MACHINE_CLASS ?= "none"

GPE_EXTRA_DEPENDS += "${GPE_EXTRA_DEPENDS_${GUI_MACHINE_CLASS}}"
GPE_EXTRA_INSTALL += "${GPE_EXTRA_INSTALL_${GUI_MACHINE_CLASS}}"

GPE_EXTRA_THEMES = "gpe-theme-industrial"

GPE_EXTRA_DEPENDS_bigscreen = "${GPE_EXTRA_THEMES}"
GPE_EXTRA_INSTALL_bigscreen = "gpe-task-games ${GPE_EXTRA_THEMES}"

GPE_EXTRA_DEPENDS_smallscreen = "${GPE_EXTRA_THEMES}"
GPE_EXTRA_INSTALL_smallscreen = "gpe-task-games ${GPE_EXTRA_THEMES}"

#ship more stuff with devices with >16MB of flash
GPE_BIGFLASH_DEPENDS := '${@base_conditional("ROOT_FLASH_SIZE", "16", "", "\
        gpe-theme-clearlooks \
	sylpheed \
        figment \
",d)}'


GPE_BIGFLASH_INSTALL := '${@base_conditional("ROOT_FLASH_SIZE", "16", "", "\
		gpe-task-connectivity \
		${GPE_BIGFLASH_DEPENDS} \
",d)}'

GPE_EXTRA_DEPENDS += ${GPE_BIGFLASH_DEPENDS}
GPE_EXTRA_INSTALL += ${GPE_BIGFLASH_INSTALL}

GPE_EXTRA_DEPENDS_none = ""
GPE_EXTRA_INSTALL_none = ""

#GPE_EXTRA_DEPENDS_h3600 = "dillo2"
#GPE_EXTRA_INSTALL_h3600 = "dillo2"


XSERVER ?= "xserver-kdrive-fbdev"

DEPENDS = "task-bootstrap \
	   meta-gpe \
	   ${GPE_EXTRA_DEPENDS}"

export IPKG_INSTALL = "task-bootstrap gpe-task-base \
           	       gpe-task-pim gpe-task-settings \
	               gpe-task-apps ${GPE_EXTRA_DEPENDS} \
		       ${XSERVER} \
		       ${GPE_EXTRA_INSTALL}"

ROOTFS_POSTPROCESS_COMMAND += "zap_root_password; "

inherit image_ipk
LICENSE = MIT
