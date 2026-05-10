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

## Dependencies

The BitBake setup config fetches these public meta layers:

- `openembedded-core`
- `meta-yocto`
- `meta-openembedded`
- `meta-arm`
- `meta-raspberrypi`

It also expects local layers:

- [`meta-homelab`](https://github.com/mdmfernandes/homelab) - layer provided by this repo
- [`meta-rpi-optee`](https://github.com/mdmfernandes/meta-rpi-optee/tree/whinlatter) - provides Raspberry Pi 4 64-bit OP-TEE BSP support

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

## Build

### Enter the Build Environment

```bash
. bitbake-builds/homelab-whinlatter/build/init-build-env
```

### Build Image

```bash
bitbake homelab-image
```

## Flash the SD Card

> [!CAUTION]
> This assumes that the SD Card is `/dev/mmcblk0`. Check before flashing (e.g. with `lsblk`)!!!

```bash
bmaptool copy --bmap \
  tmp/deploy/images/raspberrypi4-64-optee/homelab-image-raspberrypi4-64-optee.rootfs.wic.bmap \
  tmp/deploy/images/raspberrypi4-64-optee/homelab-image-raspberrypi4-64-optee.rootfs.wic.bz2 \
  /dev/mmcblk0
```

> [!NOTE]
> The command needs to be executed by **root** or other user with the required privileges.

## Boot and Run Examples

### Boot Logs

An example of the expected boot logs is available at [./docs/boot-example.txt].

### OP-TEE Examples

OP-TEE provides multiple examples (that we install in the rootfs) that can be executed from the user-space. The name
of the examples start with `optee_example_*`.

For instance, to run the `hello_world` example:

```bash
optee_example_hello_world
```

Expected output:

```text
D/TC:? 0 tee_ta_init_pseudo_ta_session:303 Lookup pseudo TA 8aaaf200-2450-11e4-abe2-0002a5d5c51b
D/TC:? 0 ldelf_load_ldelf:110 ldelf load address 0x40007000
D/LD:  ldelf:142 Loading TS 8aaaf200-2450-11e4-abe2-0002a5d5c51b
D/TC:? 0 ldelf_syscall_open_bin:163 Lookup user TA ELF 8aaaf200-2450-11e4-abe2-0002a5d5c51b (Secure Storage TA)
I/TC: WARNING (insecure configuration): Failed to get monotonic counter for REE FS, using 0
D/TC:? 0 ldelf_syscall_open_bin:167 res=0xffff0008
D/TC:? 0 ldelf_syscall_open_bin:163 Lookup user TA ELF 8aaaf200-2450-11e4-abe2-0002a5d5c51b (REE)
D/TC:? 0 ldelf_syscall_open_bin:167 res=0
D/LD:  ldelf:176 ELF (8aaaf200-2450-11e4-abe2-0002a5d5c51b) at 0x40087000
D/TA:  TA_CreateEntryPoint:39 has been called
D/TA:  __GP11_TA_OpenSessionEntryPoint:68 has been called
I/TA: Hello World!
InvokingD/TA:  inc_value:105 has been called
 TA to iI/TA: Got value: 42 from NW
ncrementI/TA: Increase value to: 43
 42
D/TC:? 0 tee_ta_close_session:461 csess 0x1018ca10 id 1
TA increD/TC:? 0 tee_ta_close_session:480 Destroy session
mented vI/TA: Goodbye!
alue to D/TA:  TA_DestroyEntryPoint:50 has been called
43
D/TC:? 0 destroy_context:318 Destroy TA ctx (0x1018c9b0)
```

### OP-TEE Tests

OP-TEE tests can be executed with:

```bash
xtest
```

For details, see [OP-TEE documentation > optee_test](https://optee.readthedocs.io/en/latest/building/gits/optee_test.html).
