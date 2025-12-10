SUMMARY = "Icansii Linux Runtime Image"
DESCRIPTION = "Custom image for OAK camera, LiDAR LD19 USB, YOLOv8 and ROS2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
inherit core-image

# Core tools
IMAGE_INSTALL:append = " \
    packagegroup-core-boot \
    util-linux \
    udev \
    systemd \
"

# Python + tools
IMAGE_INSTALL:append = " \
    python3 \
    python3-pip \
    python3-numpy \
    python3-opencv \
    python3-pyserial \
"

# BLE / WiFi support
IMAGE_INSTALL:append = " \
    bluez5 \
    bluez5-testtools \
    wpa-supplicant \
"

# OAK DepthAI
IMAGE_INSTALL:append = " \
    oak-camera \
"

# LiDAR LD19 USB
IMAGE_INSTALL:append = " \
    lidar-ld19 \
"

# YOLOv8
IMAGE_INSTALL:append = " \
    yolov8-depthai \
"

# Enable SSH
IMAGE_INSTALL:append = " \
    openssh \
"

# ROS2 (si ton layer ROS est actif)
IMAGE_INSTALL:append = " \
    ros-base \
"
