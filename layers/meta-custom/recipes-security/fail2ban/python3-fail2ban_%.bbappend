FILESEXTRAPATHS:prepend := "${THISDIR}/files:"


SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    install -m 0644 ${WORKDIR}/jail.local ${D}${sysconfdir}/fail2ban/jail.local
}
