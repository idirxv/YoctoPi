#!/bin/sh

# Yocto environment setup script for YoctoPi
# This script must be launched from the project root

# Check if the script is launched from the project root
if [ ! -d "layers" ] || [ ! -d "layers/meta-custom" ]; then
    echo "Error: This script must be launched from the YoctoPi project root."
    echo "Usage: source setup-yocto-env.sh [build_dir]"
    return 1
fi

# Determine the build directory to use
BUILD_DIR=${1:-build}

# Detect the root directory
if [ "$BASH_SOURCE" ]; then
    # For bash
    SCRIPT_PATH=$(readlink -f "${BASH_SOURCE[0]}")
elif [ "$ZSH_NAME" ]; then
    # For zsh
    SCRIPT_PATH="${(%):-%x}"
    SCRIPT_PATH=$(readlink -f "$SCRIPT_PATH")
else
    echo "Error: Unsupported shell. Please use bash or zsh."
    return 1
fi
SCRIPT_DIR=$(dirname "$SCRIPT_PATH")
ROOT_DIR=$(dirname "$(dirname "$SCRIPT_DIR")")

# Check if the poky directory exists
if [ ! -d "${ROOT_DIR}/layers/poky" ]; then
    echo "Error: The 'poky' directory was not found in ${ROOT_DIR}/layers/"
    echo "Make sure the poky repository is initialized."
    return 1
fi

# Path to the sample configuration files
TEMPLATE_DIR="${ROOT_DIR}/layers/meta-custom/conf/templates/custom"
export TEMPLATECONF="${TEMPLATE_DIR}"

# Check if configuration files already exist
if [ ! -f "${BUILDDIR}/conf/bblayers.conf" ] || [ ! -f "${BUILDDIR}/conf/local.conf" ]; then
    echo "Setting up configuration files..."
    OE_INIT="${ROOT_DIR}/layers/poky/oe-init-build-env"

    # Source the base Yocto environment
    source "${OE_INIT}" "$BUILD_DIR"
else
    echo "Configuration files already exist in ${BUILDDIR}/conf/"
    echo "To regenerate the configuration, delete or rename these files."
fi

echo ""
echo "====================================================================="
echo "Yocto environment successfully configured!"
echo "Build directory: ${BUILDDIR}"
echo "====================================================================="
echo ""
