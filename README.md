# YoctoPi

Custom Yocto Linux distribution for Raspberry Pi 4 with Docker, WiFi auto-connect, SSH, and security hardening.

## Build Environment

This project uses the [crops/yocto:debian-11-base](https://hub.docker.com/r/crops/yocto) Docker image for building.

```bash
# Pull the image
docker pull crops/yocto:debian-11-base

# Run the container
docker run --rm -it -v $(pwd):/home/yoctouser/YoctoPi crops/yocto:debian-11-base
```

## Quick Start

1. **Clone & setup:**
   ```bash
   git clone --recursive https://github.com/yourusername/YoctoPi.git
   cd YoctoPi
   source setup-env.sh
   ```

2. **Build:**
   ```bash
   bitbake core-image-base-docker
   ```

3. **Flash to SD card:**
   ```bash
   # Option 1: dd
   sudo dd if=build/tmp/deploy/images/raspberrypi4-64/core-image-base-docker-raspberrypi4-64.wic.gz of=/dev/sdX bs=4M status=progress

   # Option 2: bmaptool (faster)
   sudo bmaptool copy core-image-base-docker-raspberrypi4-64.rootfs.wic /dev/sdX --bmap core-image-base-docker-raspberrypi4-64.rootfs.wic.bmap
   ```

## Components & Configuration

### Images

* **core-image-base-docker:** A Docker and Docker Compose ready image.
    - Edit the `dockeruser` password in [layers/meta-custom/recipes-core/images/core-image-base-docker.bb](layers/meta-custom/recipes-core/images/core-image-base-docker.bb).

### Installable Packages

* **wifi-autoconnect:** Enables WiFi auto-connect at boot using `wpa_supplicant`.
    - Edit WiFi credentials in [layers/meta-custom/recipes-connectivity/wifi-autoconnect/files/wpa_supplicant.conf](layers/meta-custom/recipes-connectivity/wifi-autoconnect/files/wpa_supplicant.conf).
* **my-ssh-key:** Provides an SSH Server with secure configuration.
    - Add ssh public key to [layers/meta-custom/recipes-core/my-ssh-key/files/authorized_keys](layers/meta-custom/recipes-core/my-ssh-key/files/authorized_keys).
    - Change the port in [sshd_config](layers/meta-custom/recipes-core/my-ssh-key/files/sshd_config), [portoverride.conf](layers/meta-custom/recipes-core/my-ssh-key/files/portoverride.conf) and don't forget [fail2ban/jail.local](layers/meta-custom/recipes-security/fail2ban/files/jail.local).
