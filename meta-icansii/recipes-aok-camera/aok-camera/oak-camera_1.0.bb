SUMMARY = "Luxonis DepthAI Python SDK for OAK cameras"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# pip doit être du target + éviter les wheels x86
DEPENDS = "python3 python3-pip"

RDEPENDS:${PN} = "\
    python3 \
    python3-numpy \
    python3-opencv \
    python3-pyserial \
    libusb1 \
"

SRC_URI = " \
    file://oak.rules \
    file://COPYING.MIT \
"

S = "${WORKDIR}"

do_install() {

    export PYTHONPATH=${D}${PYTHON_SITEPACKAGES_DIR}

    # Installer depthai directement dans le filesystem cible
    ${STAGING_BINDIR}/python3 -m pip install \
        --no-deps \
        --no-cache-dir \
        --prefix=/usr \
        --root=${D} \
        depthai

    # installer udev
    install -d ${D}/etc/udev/rules.d
    install -m 0644 ${WORKDIR}/oak.rules ${D}/etc/udev/rules.d/
}

# Empêcher Yocto de strip/objcopy les wheels Python
INSANE_SKIP:${PN} += "already-stripped dev-so file-rdeps"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES:${PN} += " \
    /etc/udev/rules.d/oak.rules \
    ${PYTHON_SITEPACKAGES_DIR} \
"
