DESCRIPTION = "Provide limited super user privileges to specific users"
LICENSE = "sudo"
HOMEPAGE = "http://www.sudo.ws/sudo/"
PRIORITY = "optional"
SECTION = "admin"

inherit autotools debian-vampyre

SRC_URI += "file://install-binaries.patch;patch=1 \
	   file://autofoo.patch;patch=1 \
	   file://noexec-link.patch;patch=1"

EXTRA_OECONF = "--with-editor=/bin/vi \
                --with-env-editor \
		--disable-setresuid \
		--libexecdir=${libdir}/sudo \
		--with-secure-path='/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/X11R6/bin'"

do_configure_prepend () {
	rm -f acsite.m4
	if [ ! -e acinclude.m4 ]; then
		cat aclocal.m4 > acinclude.m4
	fi
}

python __anonymous () {
	bb.data.setVarFlag("do_install", "fakeroot", 1, d)
}

do_fix_perms () {
	# fakeroot fucks this one up
	chmod 0400 ${D}${sysconfdir}/sudoers
}
