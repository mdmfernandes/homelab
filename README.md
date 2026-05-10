# Home Lab

Home lab Yocto image for Raspberry Pi 4 with OP-TEE

## Responsibilities

- Defines the `homelab` distro in `layers/meta-homelab/conf/distro/homelab.conf`.
- Defines `homelab-image` in `layers/meta-homelab/recipes-core/images/homelab-image.bb`.
- Provides BitBake setup configuration
- Provides setup fragments for `DISTRO` and `MACHINE` selection in the configuration.

The image target is:

```bash
bitbake homelab-image
```

## Setup

### Initialize

Select machine via an interactive prompt.

> [!NOTE]
> For now only `machine/raspberrypi4-64-optee` is tested

```bash
bitbake-setup \
  --setting default top-dir-prefix /path/to/top/dir \
  --setting default top-dir-name bitbake-builds \
  init \
  homelab.conf.json \
  homelab distro/homelab
```

#### Initialize for Raspberry Pi 4 with OP-TEE

```bash
bitbake-setup \
  --setting default top-dir-prefix /path/to/top/dir \
  --setting default top-dir-name bitbake-builds \
  init \
  --non-interactive \
  homelab.conf.json \
  homelab distro/homelab machine/raspberrypi4-64-optee
```

### Enter the Build Environment

```bash
. bitbake-builds/homelab-whinlatter/build/init-build-env
```

### Build Image

```bash
bitbake homelab-image
```

## Dependencies

The BitBake setup config fetches these public `whinlatter` layers:

- `openembedded-core`
- `meta-yocto`
- `meta-openembedded`
- `meta-arm`
- `meta-raspberrypi`

It also expects local layers:

- [`meta-homelab`](https://github.com/mdmfernandes/homelab) - layer provided by this repo
- [`meta-rpi-optee`](https://github.com/mdmfernandes/meta-rpi-optee/tree/whinlatter) - provides Raspberry Pi 4 64-bit OP-TEE BSP support
