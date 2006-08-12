DESCRIPTION = "Screen is a full-screen window manager \
that multiplexes a physical terminal between several \
processes, typically interactive shells."
LICENSE = "GPL"
SECTION = "console/utils"
DEPENDS = "ncurses"

inherit autotools debian-vampyre

SRC_URI += "file://configure.patch;patch=1"

EXTRA_OECONF = "--with-pty-mode=0620 --with-pty-group=5"
