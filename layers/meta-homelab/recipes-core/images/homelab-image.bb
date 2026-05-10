# TODO: remove this? Need to add some features
require recipes-core/images/core-image-minimal.bb

SUMMARY = "Home Lab image for Raspberry Pi 4 with OP-TEE"
DESCRIPTION = "Home Lab image for Raspberry Pi 4 with OP-TEE support. This image includes the necessary packages and tools to set up a home lab environment on a Raspberry Pi 4, with support for OP-TEE (Open Portable Trusted Execution Environment)."
LICENSE = "MIT"

IMAGE_FEATURES += "package-management ssh-server-openssh"

IMAGE_INSTALL:append = " \
    packagegroup-tee-optee \
    packagegroup-tee-tools \
    libstdc++ \
    mtd-utils \
    openssl \
"
