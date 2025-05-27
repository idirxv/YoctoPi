FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://sshd_config \
            file://portoverride.conf \
           "

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/sshd.socket.d
    install -m 0644 ${WORKDIR}/sshd_config ${D}${sysconfdir}/ssh/sshd_config
    install -m 0644 ${WORKDIR}/portoverride.conf ${D}${systemd_unitdir}/system/sshd.socket.d/portoverride.conf
}
