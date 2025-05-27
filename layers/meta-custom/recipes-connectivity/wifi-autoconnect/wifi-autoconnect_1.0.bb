DESCRIPTION = "WiFi auto-connect configuration and services"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

# wpa-supplicant service and configuration
SRC_URI = " \
    file://wpa_supplicant-wlan0.service \
    file://wpa_supplicant.conf \
    file://wlan0.network \
"

do_install() {
    # Install wpa_supplicant service file
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/wpa_supplicant-wlan0.service ${D}${systemd_system_unitdir}

    # Install network configuration
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/wlan0.network ${D}${sysconfdir}/systemd/network/

    # Install wpa_supplicant configuration
    install -d ${D}${sysconfdir}/wpa_supplicant
    install -m 0600 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant/
}

FILES:${PN} += "\
    ${systemd_system_unitdir}/wpa_supplicant-wlan0.service \
    ${sysconfdir}/wpa_supplicant/wpa_supplicant.conf \
    ${sysconfdir}/systemd/network/wlan0.network \
"

# Ensure the required packages are installed
RDEPENDS:${PN} = " \
    systemd \
    wpa-supplicant \
"

# Make sure the service starts after network is available
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_SERVICE:${PN} = "wpa_supplicant-wlan0.service"
