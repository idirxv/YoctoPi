DESCRIPTION = "Deploys authorized_keys for SSH login."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

# SSH public key to deploy, should be set in local.conf
SSH_PUBLIC_KEY ??= ""

do_install() {
    if [ -z "${SSH_PUBLIC_KEY}" ]; then
        bbfatal "SSH_PUBLIC_KEY must be set in local.conf to deploy SSH keys"
    fi

    echo "${SSH_PUBLIC_KEY}" > ${WORKDIR}/authorized_keys

    # Install for ypiuser
    install -d -m 0700 ${D}/home/ypiuser/.ssh
    install -m 0600 ${WORKDIR}/authorized_keys ${D}/home/ypiuser/.ssh/authorized_keys
}

FILES:${PN} = "\
    /home/ypiuser/.ssh/authorized_keys \
"

pkg_postinst_ontarget:${PN}() {
    chown -R ypiuser:ypiuser /home/ypiuser/.ssh
}
