SECTION = "devel"
DESCRIPTION = "Sanitized set of 2.6 kernel headers for the C library's use."
MAINTAINER = "Chris Larson <kergoth@handhelds.org>"
INHIBIT_DEFAULT_DEPS = "1"
PR = "r3"
LICENSE = "GPL"
# NOTE: no need to package these headers, since the c library includes them.
PACKAGES = ""

SRC_URI = "http://ep09.pld-linux.org/~mmazur/linux-libc-headers/linux-libc-headers-${PV}.tar.bz2 \
	   file://sh-missing.patch;patch=1 \
	   file://keyboard.patch;patch=1"
S = "${WORKDIR}/linux-libc-headers-${PV}"

do_configure () {
	case ${TARGET_ARCH} in
		alpha*)   ARCH=alpha ;;
		arm*)     ARCH=arm ;;
		cris*)    ARCH=cris ;;
		hppa*)    ARCH=parisc ;;
		i*86*)    ARCH=i386 ;;
		ia64*)    ARCH=ia64 ;;
		mips*)    ARCH=mips ;;
		m68k*)    ARCH=m68k ;;
		powerpc*) ARCH=ppc ;;
		s390*)    ARCH=s390 ;;
		sh*)      ARCH=sh ;;
		sparc64*) ARCH=sparc64 ;;
		sparc*)   ARCH=sparc ;;
		x86_64*)  ARCH=x86_64 ;;
	esac
	if test !  -e include/asm-$ARCH; then
		oefatal unable to create asm symlink in kernel headers
	fi
	cp -pPR "include/asm-$ARCH" "include/asm"
	if test "$ARCH" = "arm"; then
		cp -pPR include/asm/arch-ebsa285 include/asm/arch
	elif test "$ARCH" = "sh"; then
		cp -pPR include/asm/cpu-${TARGET_ARCH} include/asm/cpu || die "unable to create include/asm/cpu"
	fi
}

do_stage () {
	install -d ${STAGING_INCDIR}
	rm -rf ${STAGING_INCDIR}/linux ${STAGING_INCDIR}/asm
	cp -pfLR include/linux ${STAGING_INCDIR}/
	cp -pfLR include/asm ${STAGING_INCDIR}/
	rm -rf ${CROSS_DIR}/${TARGET_SYS}/include/linux
	rm -rf ${CROSS_DIR}/${TARGET_SYS}/include/asm
	install -d ${CROSS_DIR}/${TARGET_SYS}/include
	cp -pfLR include/linux ${CROSS_DIR}/${TARGET_SYS}/include/
	cp -pfLR include/asm ${CROSS_DIR}/${TARGET_SYS}/include/

	ln -sf ${STAGING_KERNEL_DIR}/include/linux/wireless.h ${STAGING_INCDIR}/linux/wireless.h
	ln -sf ${STAGING_KERNEL_DIR}/include/linux/wireless.h ${CROSS_DIR}/${TARGET_SYS}/include/linux/wireless.h
}
