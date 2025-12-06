FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Default fail2ban configuration, can be overridden in local.conf
SSH_PORT ??= "22"

SRC_URI += "\
    file://jail.local \
    file://sshd.local \
    file://caddy.local \
    file://caddy-auth.conf \
"

SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    # Substitute variables in sshd.local
    sed -i 's|@SSH_PORT@|${SSH_PORT}|g' ${WORKDIR}/sshd.local

    install -d ${D}${sysconfdir}/fail2ban
    install -d ${D}${sysconfdir}/fail2ban/jail.d
    install -d ${D}${sysconfdir}/fail2ban/filter.d

    # Jails
    install -m 0644 ${WORKDIR}/jail.local ${D}${sysconfdir}/fail2ban/jail.local
    install -m 0644 ${WORKDIR}/sshd.local ${D}${sysconfdir}/fail2ban/jail.d/sshd.local
    install -m 0644 ${WORKDIR}/caddy.local ${D}${sysconfdir}/fail2ban/jail.d/caddy.local

    # Filters
    install -m 0644 ${WORKDIR}/caddy-auth.conf ${D}${sysconfdir}/fail2ban/filter.d/caddy-auth.conf
}
