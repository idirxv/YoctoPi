require recipes-core/images/core-image-base.bb

# --- Core Settings ---
# Enable SSH
IMAGE_FEATURES += "ssh-server-openssh"

# Package management
IMAGE_FEATURES += "package-management"
IMAGE_INSTALL:append = " apt"

# Set default timezone
IMAGE_INSTALL:append = " tzdata"
DEFAULT_TIMEZONE = "Europe/Paris"

# --- Container & Virtualization ---
IMAGE_INSTALL:append = " \
    docker \
    docker-compose \
"

# --- Hardware Acceleration (Rockchip RK3588) ---
IMAGE_INSTALL:append = " \
    rockchip-libmali \
    rockchip-librga \
    rockchip-mpp \
    rockchip-npu \
    udev-conf-rockchip \
"

# --- NAS, Storage & Filesystems ---
IMAGE_INSTALL:append = " \
    btrfs-tools \
    fuse3 \
    hdparm \
    mdadm \
    nfs-utils \
    rclone \
    samba \
    smartmontools \
"

# --- Networking & Security ---
IMAGE_INSTALL:append = " \
    caddy \
    curl \
    gnupg \
    iperf3 \
    iptables \
    python3-fail2ban \
    ufw \
    wireguard-tools \
"

# --- System Tools & Utilities ---
IMAGE_INSTALL:append = " \
    apt \
    ca-certificates \
    git \
    htop \
    jq \
    log2ram \
    nano \
    resize-rootfs \
    rsyslog \
    sudo \
"

# Add user creation
inherit extrausers

# Password: "pass" (SHA512 hash)
YPI_PASSWORD_HASH ??= "\$6\$9zy7484WukBEu6D8\$TnxDaRn7/DFtw2ZcyW.26C.x76R1RN/X54eCnkD6QySeKA2YjZR7nNVLiqbps7uUlNiXCrfCLF36OB1i0LZ2s."

# Create 'ypiuser', set passwords, and add groups
EXTRA_USERS_PARAMS = "\
    usermod -p '*' -s /bin/bash root; \
    useradd -p '${YPI_PASSWORD_HASH}' -d /home/ypiuser -m -s /bin/bash ypiuser; \
    usermod -aG docker,video,render,sudo ypiuser; \
"

enable_sudo_group() {
    install -d ${IMAGE_ROOTFS}/etc/sudoers.d
    echo "%sudo ALL=(ALL) ALL" > ${IMAGE_ROOTFS}/etc/sudoers.d/ypi-sudo
    chmod 0440 ${IMAGE_ROOTFS}/etc/sudoers.d/ypi-sudo
}
ROOTFS_POSTPROCESS_COMMAND += "enable_sudo_group;"
