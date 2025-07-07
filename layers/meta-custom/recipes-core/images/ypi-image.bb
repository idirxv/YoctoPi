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

# Set dockeruser password (dockerpass) and group
EXTRA_USERS_PARAMS = "\
    useradd -p '\$6\$S8cPTQJi9ze9yek4\$StA5d9DwklinSewNtHOgFH6dunVBzkPwMoDOuzWAlBRTUTf9FhjCmqayTYzYekM1SbU0S6rOZDlqcBIswNg9R1' dockeruser; \
    usermod -aG docker dockeruser; \
"

# Remove root password
EXTRA_USERS_PARAMS += "usermod -p '*' root;"

# Add SSH keys for root
IMAGE_INSTALL:append = " my-ssh-key"

# WiFi auto-connect
IMAGE_INSTALL:append = " wifi-autoconnect"

## Security
IMAGE_INSTALL:append = " iptables ufw python3-fail2ban"
