# The files/libraries that needed to be included and the do_install function are attributed to Piyush Nagpal

# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# DONE: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
# SRC_URI = "git@github.com:cu-ecen-aeld/assignments-3-and-later-alken2.git;protocol=ssh;branch=main \
			 file://aesdsocket-start-stop \
			"

PV = "1.0+git${SRCPV}"
SUMMARY = "aesd-assignments - aesdsocket"
# DONE: set to reference a specific commit hash in your assignment repo
SRCREV = "6d1e18f4c885ee2c2e6c60cee0b4dde6cf5279cb"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# DONE: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += " \
    ${bindir}/aesdsocket \
    ${bindir}/aesdsocket-start-stop.sh \
    ${systemd_system_unitdir}/aesdsocket.service \
"
# DONE: customize these as necessary for any libraries you need for your application
# (and remove comment)
TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket

    install -d ${D}${sysconfdir}/rcS.d
    ln -sf ../init.d/aesdsocket ${D}${sysconfdir}/rcS.d/S99aesdsocket
}
