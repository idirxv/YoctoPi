# YoctoPi

Custom Yocto Linux distribution for Raspberry Pi 4.

## Build Environment

This project uses the [crops/yocto:debian-11-base](https://hub.docker.com/r/crops/yocto) Docker image for building.
```bash
# Pull the image
docker pull crops/yocto:debian-11-base

# Run the container
docker run --rm -it -v $(pwd):/home/yoctouser/YoctoPi crops/yocto:debian-11-base
```

## Quick Start

### Build
```bash
source setup-env.sh
bitbake ypi-image
```

### Flash to SD Card
```bash
cd build/tmp/deploy/images/raspberrypi4-64

# Option 1: dd
sudo dd if=ypi-image-raspberrypi4-64.wic.gz of=/dev/sdX bs=4M status=progress conv=fsync

# Option 2: bmaptool (faster)
sudo bmaptool copy ypi-image-raspberrypi4-64.rootfs.wic /dev/sdX --bmap ypi-image-raspberrypi4-64.rootfs.wic.bmap
```

**Note:** Replace `/dev/sdX` with your actual SD card device (e.g., `/dev/sdb`). Verify with `lsblk` before flashing.

## System Specifications

### Core System
* **Yocto Version:** scarthgap
* **Linux Kernel:** 6.6
* **Init System:** systemd
* **Package Format:** DEB (apt)

### Hardware Configuration (Raspberry Pi 4)
* **Bootloader:** U-Boot enabled
* **GPU Memory:** 256MB
* **Filesystem:** 2GB minimum rootfs, 16GB extra space allocated

### Installed Packages

**Web/Networking:**
* caddy - Web server and reverse proxy
* wireguard-tools - VPN connectivity

**Containerization:**
* docker

**Misc:**
* fail2ban - Intrusion prevention (jails: sshd, caddy)
* log2ram - Log management for SD card longevity
* python3-telegram-bot - Telegram bot library

### User Configuration
* **Default user:** `ypiuser` (default password: `pass`)
* **Root login:** Disabled (password locked)
* **SSH:** Enabled via key authentification

### Optional Settings

Configure these in `local.conf` before building:

* SSH_PORT : Custom SSH port
* SSH_PUBLIC_KEY : Authorized public key for SSH authentication
* YPI_PASSWORD_HASH : Custom ypiuser password hash
