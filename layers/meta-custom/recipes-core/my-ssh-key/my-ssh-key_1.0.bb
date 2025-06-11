DESCRIPTION = "Deploys root's authorized_keys for SSH login."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

SRC_URI = "file://authorized_keys"

do_install() {
    install -d ${D}/home/root/.ssh
    install -m 0600 ${WORKDIR}/authorized_keys ${D}/home/root/.ssh/authorized_keys
    chown root:root ${D}/home/root/.ssh/authorized_keys
}

FILES:${PN} += "/home/root/.ssh/authorized_keys"
