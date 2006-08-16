DESCRIPTION = "Miscellaneous files for the base system."
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPL"
PR = "r49"

RDEPENDS = " initscripts (>= 1.0-r62)"

SRC_URI = " \
           file://nsswitch.conf \
           file://motd \
           file://inputrc \
           file://host.conf \
           file://profile \
           file://fstab \
	   file://filesystems \
           file://issue.net \
           file://issue \
           file://shells \
           file://usbd \
           file://share/dot.bashrc \
           file://share/dot.profile \
           file://licenses/BSD \
           file://licenses/GPL-2 \
           file://licenses/LGPL-2 \
           file://licenses/LGPL-2.1 \
           file://licenses/Artistic "
S = "${WORKDIR}"


PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "${PN}-doc ${PN}"
FILES_${PN} = "/"
FILES_${PN}-doc = "${docdir}"


CONFFILES_${PN} = "${sysconfdir}/resolv.conf \
                   ${sysconfdir}/fstab \
		   ${sysconfdir}/hostname"


docdir_append = "/${P}"
dirs1777 = "/tmp ${localstatedir}/lock ${localstatedir}/tmp"
dirs2775 = "/home ${prefix}/src ${localstatedir}/local"
dirs755 = "/bin /boot /dev ${sysconfdir} ${sysconfdir}/default \
	   ${sysconfdir}/skel /lib /mnt /proc /home/root /sbin \
	   ${prefix} ${bindir} ${docdir} /usr/games ${includedir} \
	   ${libdir} ${sbindir} ${datadir} \
	   ${datadir}/common-licenses ${datadir}/dict ${infodir} \
	   ${mandir} ${datadir}/misc ${localstatedir} \
	   ${localstatedir}/backups ${localstatedir}/cache \
	   ${localstatedir}/lib /sys ${localstatedir}/lib/misc \
	   ${localstatedir}/lock/subsys ${localstatedir}/log \
	   ${localstatedir}/run ${localstatedir}/spool \
	   /mnt /media /media/card /media/cf /media/net /media/ram \
	   /media/hdd \
           /media/mmc1"
conffiles = "${sysconfdir}/debian_version ${sysconfdir}/host.conf \
	     ${sysconfdir}/inputrc ${sysconfdir}/issue /${sysconfdir}/issue.net \
	     ${sysconfdir}/nsswitch.conf ${sysconfdir}/profile \
	     ${sysconfdir}/default"

def filter_tmpfs(files, d):
	r = []
	for f in files.split():
		if f.startswith("${localstatedir}") or f.startswith("/var"):
			r.append(f)
	return " ".join(r)

tmpfsdirs1777 = "${@filter_tmpfs(bb.data.getVar("dirs1777", d, 0), d)}"
tmpfsdirs2775 = "${@filter_tmpfs(bb.data.getVar("dirs2775", d, 0), d)}"
tmpfsdirs755 = "${@filter_tmpfs(bb.data.getVar("dirs755", d, 0), d)}"
tmpfsfiles664 = "${localstatedir}/run/utmp \
		 ${localstatedir}/log/wtmp \
		 ${localstatedir}/log/lastlog"

hostname = "openembedded"

do_install () {
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done
	for d in ${dirs1777}; do
		install -m 1777 -d ${D}$d
	done
	for d in ${dirs2775}; do
		install -m 2755 -d ${D}$d
	done
	for d in card cf net ram; do
		ln -sf /media/$d ${D}/mnt/$d
	done

	if [ -n "${MACHINE}" -a "${hostname}" = "openembedded" ]; then
		echo ${MACHINE} > ${D}${sysconfdir}/hostname
	else
		echo ${hostname} > ${D}${sysconfdir}/hostname
	fi

        if [ -n "${DISTRO_NAME}" ]; then
		echo -n "${DISTRO_NAME} " > ${D}${sysconfdir}/issue
		echo -n "${DISTRO_NAME} " > ${D}${sysconfdir}/issue.net
		if [ -n "${DISTRO_VERSION}" ]; then
			echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue
			echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue.net
		fi
		echo "\n \l" >> ${D}${sysconfdir}/issue
		echo >> ${D}${sysconfdir}/issue
		echo "%h"    >> ${D}${sysconfdir}/issue.net
		echo >> ${D}${sysconfdir}/issue.net
	else
 	       install -m 0644 ${WORKDIR}/issue ${D}${sysconfdir}/issue
 	       install -m 0644 ${WORKDIR}/issue.net ${D}${sysconfdir}/issue.net
 	fi

	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
	install -m 0644 ${WORKDIR}/filesystems ${D}${sysconfdir}/filesystems
	install -m 0644 ${WORKDIR}/usbd ${D}${sysconfdir}/default/usbd
	install -m 0644 ${WORKDIR}/profile ${D}${sysconfdir}/profile
	install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
	install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
	install -m 0644 ${WORKDIR}/inputrc ${D}${sysconfdir}/inputrc
	install -m 0644 ${WORKDIR}/nsswitch.conf ${D}${sysconfdir}/nsswitch.conf
	install -m 0644 ${WORKDIR}/host.conf ${D}${sysconfdir}/host.conf
	install -m 0644 ${WORKDIR}/motd ${D}${sysconfdir}/motd
	install -m 0644 ${WORKDIR}/shells ${D}${sysconfdir}/shells

	for license in BSD GPL-2 LGPL-2 LGPL-2.1 Artistic; do
		install -m 0644 ${WORKDIR}/licenses/$license ${D}${datadir}/common-licenses/
	done

	if (grep -q "^\(tmpfs\|ramfs\)\W\+/var" ${D}${sysconfdir}/fstab); then
		# remove from package
		rm -rf ${D}${localstatedir}/*

		# create on boot
		install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/rcS.d
		echo "mkdir -p ${tmpfsdirs1777}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "chmod 1777 ${tmpfsdirs1777}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "mkdir -p ${tmpfsdirs2775}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "chmod 2775 ${tmpfsdirs2775}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "mkdir -p ${tmpfsdirs755}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "chmod 0755 ${tmpfsdirs755}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "touch ${tmpfsfiles664}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		echo "chmod 0664 ${tmpfsfiles664}" >> ${D}${sysconfdir}/init.d/populate-var.sh
		chmod 0755 ${D}${sysconfdir}/init.d/populate-var.sh
		ln -sf ../init.d/populate-var.sh ${D}${sysconfdir}/rcS.d/S37populate-var.sh

		# ship symlinks
		ln -sf ${localstatedir}/run/resolv.conf ${D}${sysconfdir}/resolv.conf
		ln -sf ${localstatedir}/ld.so.cache ${D}${sysconfdir}/ld.so.cache
	fi

	ln -sf /proc/mounts ${D}${sysconfdir}/mtab
}
