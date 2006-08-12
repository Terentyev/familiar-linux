DESCRIPTION = "A file-synchronization tool"
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "GPL"

DEPENDS = "popt"

inherit autotools debian-vampyre

EXTRA_OEMAKE='STRIP=""'
