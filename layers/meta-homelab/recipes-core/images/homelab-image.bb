SUMMARY = "Home Lab image for Raspberry Pi 4 with OP-TEE"
DESCRIPTION = "Home Lab image for Raspberry Pi 4 with OP-TEE support. This image includes the necessary packages and tools to set up a home lab environment on a Raspberry Pi 4, with support for OP-TEE (Open Portable Trusted Execution Environment)."
LICENSE = "MIT"

inherit core-image

# TODO: remove empty-root-password after bring-up is done
IMAGE_FEATURES += "package-management ssh-server-openssh empty-root-password"

IMAGE_INSTALL = "\
    packagegroup-core-boot \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    packagegroup-tee-optee \
    packagegroup-tee-tools \
    libstdc++ \
    mtd-utils \
    openssl \
    vim \
"
