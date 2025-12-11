SUMMARY = "LDROBOT DTOF LiDAR ROS2 package"
DESCRIPTION = "ROS2 driver for LDROBOT LiDAR (LD06, LD19, STL-27L)"
HOMEPAGE = "https://github.com/ldrobotSensorTeam/ldlidar_stl_ros2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "https://github.com/ldrobotSensorTeam/ldlidar_stl_ros2.git;branch=master"
SRCREV = "bf668a89baf722a787dadc442860dcbf33a82f5a"

# Si votre package utilise CMake / ament_cmake
inherit ros_ament_cmake
 

