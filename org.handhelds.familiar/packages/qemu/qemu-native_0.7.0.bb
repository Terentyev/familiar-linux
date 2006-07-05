include qemu_${PV}.bb
inherit native
S = "${WORKDIR}/qemu-${PV}"
prefix = "${STAGING_DIR}/${BUILD_SYS}"

python __anonymous () {
    from bb import which, data
       
    cc = 'gcc'
    path = data.getVar('PATH', d)
    if len(which(path, 'gcc-3.4')) != 0:
        cc = 'gcc-3.4'
        data.setVar('EXTRA_OECONF', " --cc=gcc-3.4", d)
    elif len(which(path, 'gcc-3.3')) != 0:
        cc = 'gcc-3.3'
        data.setVar('EXTRA_OECONF', " --cc=gcc-3.3", d)
    data.setVar('QEMU_CC', cc, d)
}

python do_check_gcc () {
    from bb import which, data
       
    import os

    cc = data.getVar('QEMU_CC', d, 1)
    f = os.popen ("%s --version" % cc, 'r')
    line = f.readline()
    if f.close() or not line:
        raise bb.build.FuncFailed("Failed to run %s" % cc)
 
    import re
 
    m = re.match("^.* \(.*\) (.\..\..).*$", line)
    if not m:
        raise bb.build.FuncFailed("Failed to parse gcc version output")
    v = m.group(1).split('.')
    if not (v[0] == '3' and v[1] in ['3', '4']):
        raise bb.build.FuncFailed("qemu requires GCC 3.3.x or 3.4.x. Please install either version to build\nthis package.\nIf you haven't explicitely asked for this package to be built, it is likely\nthat ENABLE_BINARY_LOCALE_GENERATION is set. To work around the problem set it\nto \"0\" (but note that this will break internationalization).")
}

addtask check_gcc before do_configure after do_patch
