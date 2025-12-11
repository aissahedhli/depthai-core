SUMMARY = "LD19 Lidar Python ROS2 nodes"
DESCRIPTION = "LD19 Lidar Python nodes for ROS2 Humble, including USB access via udev rules"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit python3native

# Dépendances nécessaires à la compilation et à l'exécution
DEPENDS = "python3 python3-numpy python3-pyserial rclpy sensor-msgs std-msgs"
RDEPENDS:${PN} = "python3 python3-numpy python3-pyserial rclpy sensor-msgs std-msgs"

# Sources locales
SRC_URI = "file://99-ld19.rules \
           file://COPYING.MIT"

# Répertoire de travail
S = "${WORKDIR}"

# Do compile vide, car pas de compilation nécessaire
do_compile() {
    :
}

do_install() {
    # Crée le répertoire pour les nodes ROS2
    install -d ${D}${bindir}

    # Copier tous les scripts Python
    #cp -r ${WORKDIR}/*.py ${D}${bindir}/

    # Crée le répertoire pour les règles udev
    install -d ${D}/etc/udev/rules.d
    install -m 0644 ${WORKDIR}/99-ld19.rules ${D}/etc/udev/rules.d/
}

# Inclure les fichiers dans le paquet final
FILES:${PN} += " \
    ${bindir} \
    /etc/udev/rules.d/99-ld19.rules \
"
