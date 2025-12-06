FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Default SSH port, can be overridden in local.conf
SSH_PORT ??= "22"

SRC_URI += "\
    file://sshd_config.in \
"

inherit systemd

PACKAGECONFIG = "systemd-sshd-service-mode"

SYSTEMD_SERVICE:${PN}-sshd = "sshd.service"
SYSTEMD_AUTO_ENABLE:${PN}-sshd = "enable"

do_install:append() {
    # Substitute SSH_PORT in configuration files
    sed -e 's|@SSH_PORT@|${SSH_PORT}|g' ${WORKDIR}/sshd_config.in > ${WORKDIR}/sshd_config
    install -m 0644 ${WORKDIR}/sshd_config ${D}${sysconfdir}/ssh/sshd_config
}
