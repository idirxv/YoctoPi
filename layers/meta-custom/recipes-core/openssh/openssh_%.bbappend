FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://sshd_config.in \
"

inherit systemd

PACKAGECONFIG = "systemd-sshd-service-mode"

SYSTEMD_SERVICE:${PN}-sshd = "sshd.service"
SYSTEMD_AUTO_ENABLE:${PN}-sshd = "enable"

do_install:append() {
    install -m 0644 ${WORKDIR}/sshd_config ${D}${sysconfdir}/ssh/sshd_config
}
