Schéma d’intégration complet pour icansii projet Yocto avec OAK Camera + YOLOv8 + LD19 Lidar + ROS2 Humble. Presentaion des couches, recettes et flux de données.

1. Matériel
OAK Camera (Luxonis DepthAI)
Connexion : USB
Fournit : flux vidéo et éventuellement détection sur VPU

LD19 Lidar :
Connexion : USB (port série /dev/ttyUSBx)
Fournit : distances / scan lidar

2. Logiciel / Yocto Layers
Layer	                                Rôle
meta-ros2-humble	            ROS2 Humble pour Yocto
meta-icansii	                Layer projet avec recettes spécifiques
meta-python	                    Dépendances Python, pip, numpy, opencv
oak-camera dans meta-icansii	- SDK Python DepthAI
                                - Règles udev
                                - Nodes ROS2 Python pour OAK
yolov8 dans meta-icansii	    YOLOv8 Python via pip + script d’inférence
LIDAR-ld19 dans meta-projet	    LD19 Python SDK + nodes ROS2 Python + udev rules

3. Recettes Yocto
A. OAK Camera
Recette : depthai-python.bb
Contenu :
Installer depthai via pip
Installer dépendances Python (numpy, opencv-python, pyserial)
Installer les règles udev (oak.rules)
Installer nodes ROS2 Python si nécessaire

B. YOLOv8
Recette : yolov8-depthai.bb
Contenu :
Installer ultralytics via pip
Copier ton script d’inférence yolov8_depthai.py
Installer éventuellement les modèles locaux
Dépend de : depthai-python (pour accéder aux flux de la caméra OAK)

C. LD19 Lidar
Recette : ld19-python.bb
Contenu :
Installer pyserial ou SDK Python du LD19
Installer les règles udev (99-ld19.rules)
Installer les nodes ROS2 Python pour LD19
Pas de dépendance C++ ou CMake

4. Flux de données
+-----------------+           +-------------------+
| OAK Camera USB  | ---> Video / Depth ---> SDK Python DepthAI ---> Node ROS2 Python
+-----------------+           +-------------------+
                                   |
                                   v
                              YOLOv8 inference
                                   |
                                   v
                             ROS2 Topic : /yolov8/detections

+-----------------+           +-------------------+
| LD19 Lidar USB  | ---> Serial Data ---> Node ROS2 Python ---> ROS2 Topic : /ld19/scan
+-----------------+           +-------------------+


OAK Camera fournit la vidéo via DepthAI Python SDK.
YOLOv8 consomme la vidéo et publie les résultats sur un topic ROS2.
LD19 Lidar publie les scans sur un topic ROS2.
Tous les nodes sont en Python, pas besoin de C++ ni de CMake pour ces périphériques.