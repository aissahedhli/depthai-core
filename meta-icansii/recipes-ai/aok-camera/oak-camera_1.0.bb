SUMMARY = "DepthAI Python SDK for OAK cameras"
DESCRIPTION = "Installs Luxonis DepthAI SDK (Python) and necessary udev rules"
LICENSE = "MIT"

inherit python3native

RDEPENDS:${PN} = "\
    python3 \
    python3-pip \
    python3-numpy \
    python3-opencv \
    python3-pyserial \
    libusb1 \
"

SRC_URI = "file://oak.rules"

S = "${WORKDIR}"

do_install() {
    # Install udev rules
    install -d ${D}/etc/udev/rules.d
    install -m 0644 ${WORKDIR}/oak.rules ${D}/etc/udev/rules.d/

    # Install the Python DepthAI SDK via pip
    pip3 install depthai --no-cache-dir --root=${D} --prefix=/usr
}

FILES:${PN} += " \
    /etc/udev/rules.d/oak.rules \
    ${PYTHON_SITEPACKAGES_DIR} \
"
