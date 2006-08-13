DESCRIPTION = "Portable Document Format (PDF) viewer"
LICENSE = "GPL"
SECTION = "x11/utils"

inherit gnome debian-vampyre

DEPENDS += "gtk+ libgnomeui libbonoboui gnome-vfs gconf gettext libglade \
	libgnomeprint libgnomeprintui gnome-common"

DSRC_URI += "file://000-checks.patch;patch=1;pnum=0 \
	file://005-gcc4.patch;patch=1;pnum=0 \
	file://006_CAN-2005-3191.patch;patch=1;pnum=0 \
	file://007_CVE-2006-0301.patch;patch=1;pnum=0 \
	file://008_security_upstream.patch;patch=1;pnum=0 \
	file://010-forward.patch;patch=1 \
	file://015-CAN-2005-0064.patch;patch=1 \
	file://016_CAN-2005-2097-loca-table-sanity.patch;patch=1"

EXTRA_OECONF = "--disable-schemas-install"

do_configure_prepend () {
	cp ${STAGING_DIR}/${HOST_SYS}/share/gnome-common/data/omf.make ${S}/help
}
