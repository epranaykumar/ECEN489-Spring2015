# Project 2 Overview

The goal of this project was to assign a MAC address to a particular person who's picture and MAC address are stored as a pair in a database. This is accomplished using four subsystems: A RF antenna, a linux server, a microcontroller with a distance sensor, and a face detection program.

The antennas used to collect the MAC addresses are cantennas. The cantennas are used to direct the range of the WIFI card to a very specific location. There are four different cantennas directed to the specific location. Each of these cantennas has a ping sensor mounted to it that is controlled by a microcontroller. The ping sensors will also be directed at the same location as the cantennas. All of the data from the four WIFI cards/cantennas and ping sensors will be sent to the server. When all of the WIFI cards are currently receiving the same MAC address and all four ping sensors indicate that a person is standing in a specific location the server will alert the face detection software which will turn on, locate the face of the person who's MAC address has been identified, take a picture of the person, and match and store that person's picture with their MAC address.

## RF Antenna
A directional antenna (Cantenna) is chosen in order to capture the MAC addresses that are only in the area of interest. "TP-LINK TL-WN722N" wifi card requires very little signal strength to receive packets correctly; therefore, attenuators or power dividers must be used to limit the gain of the antenna, so that the footprint of the cantenna does not cover too much area in the radial direction of the cantenna.

When performing the experiment to determine the attenuation required to build the power divider, it was found that the "TP-LINK TL-WN722N" wifi card has very poor isolation to stop RF signal from "leaking" into the chipset. It means that even without any antenna attached to the wifi card, it is still capable of capturing the packets over the air. It may be a nice feature for day to day wifi use, but "leaky" wifi card is not the best choice for this project.In the tempt to fix the "leakage", copper tape was wrapped all around the wifi card, and the tape was connected to the ground, but it did not stop the RF signal from getting into the wifi card. It is suspected that the currents formed on the copper tape when the RF signal is incident to the copper, and the wrapped copper tape is acting like a cavity resonator; therefore, attenuators or power dividers are not used at the end.

Received Signal Strength Indicator (RSSI) of the captured packet is used to determine the distance between the device and the antennas. RSSI values recevied by all four wifi cards reveal if a device is in the center of the area of interest because all four RSSI values are the closest to each other only when the device is the center.

## Server
The server manages the entire system.  The sensing boxes connect to the server and supply it with all relevant information (MAC addresses, ping sensor feedback, and OpenCV communication).  All communication occurs over Java sockets, and the communications messages all take the form of JSON objects, except in the case of the OpenCV images, which are sent directly to the server through an FTP link (see OpenCV section for more details).  

Two-way communication is required for the OpenCV subsystem.  The server sends "take_picture" commands to the sensing boxes, which then pass that command along to the OpenCV devices.  When a picture is taken successfully, the OpenCV devices pass a confirmation message back, along with the filename of the picture sent via FTP.  This message is passed through the sensing boxes and to the server, which stores this information.

The server decides when to invoke the OpenCV system using two criteria:
- X number of ping sensors go off within an allotted amount of time (currently 3/4 in a space of 0.5 seconds)
- The same MAC address is detected on all four sensing boxes (currently disabled due to reliability problems with regard to sniffing MAC addresses of cell phones)

All sniffing data is stored on the server side in SQL databases.  The database has 6 columns:
- ID (Key, autoincrement)
- Sensing box ID for sniffed address
- MAC address sniffed
- Timestamp of sniffing
- Signal strength (in dB)
- Associated OpenCV filename (null if not applicable)

If sensing boxes are placed appropriately, this information provides the ability to roughly track the motion of a MAC address in a 2-D space using timestamps and RSSI information. (untested)

## Sensing Box
The sensing box is the communications hub of each sensor system.  It performs wireless MAC sniffing while also passing messages between the server and its associated sensors.  Each sensing box has a single ping sensor and OpenCV-enabled camera assigned to it.  Additionally, it connects to a server that stores sniffed MAC addresses and manages the OpenCV component of the system.

To reduce complexity, the OpenCV component of the project does not pass images through the Sensing Box through the JSON messaging system.  Additional details can be found in the OpenCV section of the presentation.

As far as MAC address collection goes, the sensing box stores all MAC addresses in a Java HashMap, where the detected MAC is the key and the signal strength at the antenna (in dB) is keyed value.  This ensures that each MAC address is stored only once per sensing cycle (1 second in this project), and that each address has an associated signal strength.

## Microcontroller/Distance Sensor

The microcontroller used is a Teensy 2.0++ being programmed using the Arduino IDE.

### Initial Plans

This microcontroller was originally going to be used to dynamically modify the gain of the cantennas using a ADL5330 Evaluation Board. The server and the microcontroller were going to communicate using a UART connection and as needed the microcontroller would have the ability to increase or reduce the range of the WIFI card through the cantenna. This plan was never fully accomplished, however, and was given up on when it was realized the ADL5330 board would not accomplish our goals.

### Operation

The microcontroller is now used to operate a ping distance sensor. These sensors will be mounted near each cantenna and will all be directed at a specific point to determine if a person is standing in that spot. The microcontroller communicates with a java program on a local machine using a UART connection. If someone is detected within the specified area, a 'Ping' JSON object is sent from the local machine to a collector box, which in turn alerts the server that a person has been detected.

![Fig. 1. Distance Sensor Data Flow](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/iandegroot/project2_distance_sensor_flow.jpg)

### Distance Specs

The ping sensors will be placed approximately 86 inches, or a little more than 7 ft, away from the target location and will be set to detect a person standing in the range of 70 to 86 inches away because this was found to be the largest distance that still had accurate readings.

### Code

The code for this subsystem is located under [/Students/iandegroot](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/iandegroot) and is contained in three programs:

* Teensy Sketch - ping_sensor 
* Local Java Program - SerialTest 
* Test Collector - Project2JSONServer

## Face Detection

The face detection system communicates with the main server and segments the face of the person standing in the overlapped area of four sensing stations and uploads it to the FTP server, upon receiving the take picture requests from the main server. The implementation of the above functionality involves the following steps  
  
1. Communication with the Server  
JSON interface is used for the communication between the main server and the face detection modules. [The format of the JSON object can be found here.] (https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Project2). And FTP server is used for the file transfer between the server and the face detection modules.  
  
2. Segmentation of Faces  
Haar cascade classification method is used for the detection of faces in an image. [The information about this algorithm can be found here](http://docs.opencv.org/modules/objdetect/doc/cascade_classification.html). This classifier is trained on over hundred images for the detection of faces. The OpenCV library provides the trained parameters for this classifier in the XML files which can be found at  
../OpenCV2.4.9/opencv/sources/data/haarcascades/haarcascade_frontalface_alt2.xml"   
  
The face Detection system consists of 2 modules  
  
1. OpenCV module  
                 This module is used for segmentation of faces and is initiated from the Java Client module.The module initiates the camera pointing towards the overlapped area of sensing stations and captures an image and runs the trained Haar classifier over the image to segment the faces. The module captures the images until at least one face is detected and saves the segmented face in .jpg format. The module was written in C++ using OpenCV libraries. 
  
2. Java Client module  
                 This module is used for communication with the main server.The module connects to the server socket and sends the device name to the server and then  waits for the "takepicture" command from the server. Upon receiving it, the module first clears the directory where the faceDetection.exe file writes the segmented faces and then runs the faceDetection.exe file and waits until the faceDetection.exe file writes the .jpg files to that specific directory. It then uploads the .jpg file to the FTP server with the filename as timestamp.jpg. The module uploads the .jpg file when there is only one face in the image i.e., when there is only one file in that directory. 
 
[The programs for the above modules can be found here](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/epranaykumar/Project2).  

## Conclusion:

Although there were problems with the gain attenuator board and the cantenna picking up MAC address we were able to continue the project with the same basic functionality using ping sensors. When the ping sensors detected that a person was present the openCV program was notified through the server to take a picture of the person's face and send it back to the server. The MAC address could not be accurately acquired using the cantennas because the internal antenna in the wifi card has too large of a range so the picture is associated with every MAC address gathered with the same time signature.