SUMMARY = "Mount /var/log in RAM and sync periodically to disk to reduce flash wear"
DESCRIPTION = "Reduce flash wear by mounting /var/log in RAM and periodically syncing to disk."
HOMEPAGE = "https://github.com/azlux/log2ram"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4739afd3d2f2b84a686039f05ed10f82"

SRC_URI = "git://github.com/azlux/log2ram.git;branch=master;protocol=https"
SRCREV = "f171449530f8984374c8f69fff3c92af813a694b"

S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_SERVICE:${PN} = "\
    log2ram.service \
    log2ram-daily.service \
    log2ram-daily.timer \
"

do_install() {
    # Binaries
    install -d ${D}${prefix}/local/bin
    install -m 0755 ${S}/log2ram          ${D}${prefix}/local/bin/log2ram
    install -m 0755 ${S}/uninstall.sh     ${D}${prefix}/local/bin/uninstall-log2ram.sh

    # Configuration
    install -d ${D}${sysconfdir}
    install -m 0644 ${S}/log2ram.conf     ${D}${sysconfdir}/log2ram.conf

    # Logrotate rules
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${S}/log2ram.logrotate ${D}${sysconfdir}/logrotate.d/log2ram

    # systemd unit files
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/log2ram.service        ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/log2ram-daily.service  ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/log2ram-daily.timer    ${D}${systemd_system_unitdir}
}

FILES:${PN} += "\
    ${systemd_system_unitdir}/log2ram.service \
    ${systemd_system_unitdir}/log2ram-daily.service \
    ${systemd_system_unitdir}/log2ram-daily.timer \
    ${sysconfdir}/log2ram.conf \
    ${sysconfdir}/logrotate.d/log2ram \
    ${prefix}/local/bin/log2ram \
    ${prefix}/local/bin/uninstall-log2ram.sh \
"

RDEPENDS:${PN} += "bash rsync"

INSANE_SKIP:${PN} += "ldflags textrel"
