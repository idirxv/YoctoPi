require recipes-core/images/core-image-base.bb

inherit core-image

# Add SSH server
IMAGE_FEATURES += "ssh-server-openssh"

# Include package management
IMAGE_FEATURES += "package-management"
PACKAGE_CLASSES = "package_ipk"
IMAGE_INSTALL:append = " opkg"

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
"

# Add user creation
inherit extrausers

# Set dockeruser password (dockerpass) and group
EXTRA_USERS_PARAMS = "\
    useradd -p '\$6\$S8cPTQJi9ze9yek4\$StA5d9DwklinSewNtHOgFH6dunVBzkPwMoDOuzWAlBRTUTf9FhjCmqayTYzYekM1SbU0S6rOZDlqcBIswNg9R1' dockeruser; \
    usermod -aG docker dockeruser; \
"
