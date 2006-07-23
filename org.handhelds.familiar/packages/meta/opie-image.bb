export IMAGE_BASENAME = "opie-image"
export IMAGE_LINGUAS = ""

DISTRO_FEEDS_IN_feed += "opie"
DISTRO_FEEDS_IN_universe += "opie"

LICENSE = "MIT"
PR = "r19"

DEPENDS = "task-bootstrap meta-opie"

extra_stuff := '${@base_conditional("ROOT_FLASH_SIZE", "16", "", "task-opie-extra-games task-opie-extra-apps task-opie-extra-styles",d)}'

INSTALL_PACKAGES = "task-bootstrap task-opie-base task-opie-base-applets \
		    task-opie-base-inputmethods task-opie-base-apps \
		    task-opie-base-settings task-opie-base-decorations \
		    task-opie-base-styles task-opie-base-pim \
		    task-opie-extra-settings \
		    task-opie-bluetooth task-opie-irda \
		    ${extra_stuff}"

export IPKG_INSTALL = "${INSTALL_PACKAGES}"

inherit image_ipk
