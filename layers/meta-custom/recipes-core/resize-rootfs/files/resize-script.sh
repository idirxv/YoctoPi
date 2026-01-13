#!/bin/sh

# Find the partition mounted on /
ROOT_PART=$(findmnt / -o source -n)

# Verify it's a block device
if [ ! -b "$ROOT_PART" ]; then
    echo "Error: root is not a block device."
    exit 1
fi

# Extract the disk and partition number
case "$ROOT_PART" in
    /dev/mmcblk*)
        # SD Card/eMMC case: /dev/mmcblk0p2 -> DISK=/dev/mmcblk0, PART_NUM=2
        ROOT_DISK=$(echo $ROOT_PART | sed 's/p[0-9]*$//')
        PART_NUM=$(echo $ROOT_PART | sed 's/.*p//')
        ;;
    /dev/sd*)
        # USB/SATA case: /dev/sda2 -> DISK=/dev/sda, PART_NUM=2
        ROOT_DISK=$(echo $ROOT_PART | sed 's/[0-9]*$//')
        PART_NUM=$(echo $ROOT_PART | sed 's/.*\([0-9]*\)$/\1/')
        ;;
    *)
        echo "Unrecognized disk format: $ROOT_PART"
        exit 1
        ;;
esac

echo "Resizing $ROOT_PART on disk $ROOT_DISK (Partition $PART_NUM)..."

# Fix GPT to use all available space
echo "Fixing GPT table..."
parted -sf "$ROOT_DISK" print

# Resize the partition to fill the disk
echo "Resizing partition..."
parted -sf "$ROOT_DISK" resizepart "$PART_NUM" 100%

# Resize the filesystem (ext4)
echo "Resizing filesystem..."
resize2fs "$ROOT_PART"

echo "Done."
