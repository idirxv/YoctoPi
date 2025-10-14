require recipes-core/images/core-image-base.bb

# Minimal rootfs size 2GB
IMAGE_ROOTFS_SIZE = "2097152"

# Add SSH server
IMAGE_FEATURES += "ssh-server-openssh"

# Package management
IMAGE_FEATURES += "package-management"
IMAGE_INSTALL:append = " apt"

# Add docker support
IMAGE_INSTALL:append = " \
    docker \
    docker-compose \
"

# Add common utilities
IMAGE_INSTALL:append = " \
    ca-certificates \
    nano \
    htop \
    git \
    curl \
    nginx \
    rclone \
    fuse3 \
    log2ram \
"

# Add user creation
inherit extrausers

# Setup root and ypiuser
# default password "pass"
YPI_PASSWORD_HASH ??= "\$6\$9zy7484WukBEu6D8\$TnxDaRn7/DFtw2ZcyW.26C.x76R1RN/X54eCnkD6QySeKA2YjZR7nNVLiqbps7uUlNiXCrfCLF36OB1i0LZ2s."
EXTRA_USERS_PARAMS = "\
    usermod -p '*' -s /bin/bash root; \
    useradd -p '${YPI_PASSWORD_HASH}' -d /home/ypiuser -m -s /bin/bash ypiuser; \
    usermod -aG docker,video,sudo ypiuser; \
"
enable_sudo_group() {
    sed -i 's/^# %sudo/%sudo/' ${IMAGE_ROOTFS}/etc/sudoers
}
ROOTFS_POSTPROCESS_COMMAND += "enable_sudo_group;"

## Security
IMAGE_INSTALL:append = " iptables ufw python3-fail2ban"

# Timezone
IMAGE_INSTALL:append = " tzdata"
DEFAULT_TIMEZONE = "Europe/Paris"
