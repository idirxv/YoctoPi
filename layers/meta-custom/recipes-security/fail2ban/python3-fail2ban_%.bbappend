FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://jail.local"

SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    install -m 0644 ${WORKDIR}/jail.local ${D}${sysconfdir}/fail2ban/jail.local
}
