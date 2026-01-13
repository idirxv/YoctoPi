DESCRIPTION = "Resize Rootfs systemd service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://resize-rootfs.service \
    file://resize-script.sh \
"

inherit systemd

SYSTEMD_SERVICE:${PN} = "resize-rootfs.service"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = " \
    e2fsprogs-resize2fs \
    parted \
    util-linux-findmnt \
"

do_install() {
    # Install the script
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/resize-script.sh ${D}${sbindir}/

    # Install the systemd service
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/resize-rootfs.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "\
    ${sbindir}/resize-script.sh \
    ${systemd_unitdir}/system/resize-rootfs.service \
"
