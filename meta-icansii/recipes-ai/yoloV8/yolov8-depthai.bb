SUMMARY = "YOLOv8 for DepthAI (OAK cameras) using Python3"
DESCRIPTION = "Run YOLOv8 object detection on DepthAI OAK cameras using the onboard VPU via ultralytics Python SDK"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://COPYING.MIT"

# Support Python3 dans Yocto
inherit python3native setuptools3

# Dépendances build
DEPENDS = "python3-pip"

# Dépendances runtime
RDEPENDS:${PN} = "python3"

S = "${WORKDIR}"

do_install() {
    # Installer YOLOv8 via pip directement dans le rootfs cible
    pip3_install --root=${D} --prefix=/usr ultralytics

    # Copier le script Python local pour l'inférence (optionnel)
    install -d ${D}${bindir}
    # install -m 0755 ${WORKDIR}/yolov8_depthai.py ${D}${bindir}/yolov8_depthai.py

    # Copier le dossier models si présent localement
    if [ -d "${WORKDIR}/models" ]; then
        install -d ${D}${datadir}/yolov8_depthai/models
        cp -r ${WORKDIR}/models/* ${D}${datadir}/yolov8_depthai/models/
    fi
}

# Inclure les scripts et modèles dans le paquet final
FILES_${PN} += " \
    ${bindir} \
    ${datadir}/yolov8_depthai \
"