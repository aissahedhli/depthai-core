SUMMARY = "Luxonis DepthAI Python bindings for OAK cameras"
DESCRIPTION = "Builds the DepthAI Python API from source using pybind11 and CMake."
HOMEPAGE = "https://github.com/luxonis/depthai-python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "\
    git://github.com/luxonis/depthai-python.git;branch=main;protocol=https \
    file://oak.rules \
"

# Choisis une version commit stable :
SRCREV = "e6f682d0d92adb46f7d6515f43b0853332f9977b"

S = "${WORKDIR}/git"

inherit cmake python3native python3targetconfig setuptools3

# Dépendances système nécessaires
DEPENDS += "\
    python3 \
    python3-pybind11 \
    python3-numpy \
    python3-opencv \
    libusb1 \
"

# Empêcher strip de casser les .so Python compilés
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so file-rdeps"

# Configuration CMake correcte pour DepthAI Python
EXTRA_OECMAKE += "\
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_EXAMPLES=OFF \
    -DPYTHON_EXECUTABLE=${PYTHON} \
"

do_configure:prepend() {
    # Corrige les chemins Python pour éviter les modules x86
    export PYTHONPATH="${STAGING_LIBDIR_NATIVE}/python${PYTHON_BASEVERSION}/site-packages"
}

do_compile() {
    # Compilation DepthAI via cmake
    cmake --build ${B} --target all
}

do_install() {
    # Installe les bindings Python compilés dans le rootfs cible
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    PYTHONPATH=${D}${PYTHON_SITEPACKAGES_DIR} \
        python3 setup.py install --root=${D} --prefix=/usr

    # Installer les udev rules pour les caméras OAK
    install -d ${D}/etc/udev/rules.d
    install -m 0644 ${WORKDIR}/oak.rules ${D}/etc/udev/rules.d/99-oak.rules
}

FILES:${PN} += "\
    ${PYTHON_SITEPACKAGES_DIR} \
    /etc/udev/rules.d/99-oak.rules \
"
