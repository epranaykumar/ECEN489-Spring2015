##Project Overview
The focus of Project 2 is to utilize techniques developed in Project 1 to detect and identify the MAC addresses of surrounding devices in addition to associating the device to the user’s picture. Several subsystems comprise the system to accomplish this task including a main server, RF “cantennas”, an Android device using the OpenCV library, and ping sensors powered by Teensy microcontrollers.  

In order to associate a picture with a devices user, several actions must occur. A single person must enter the detection area which is surrounded by the directional antennas and ping sensors. The ping sensors will then trigger a command to the server which activates the Android devices camera. The camera snaps a picture of the person which is then processed using the OpenCV software to extract their face from the image. Finally, the antennas arranged around the detection area are used to filter out extraneous MAC addresses leaving only the present device’s MAC address. The captured MAC address is then able to be associated with that user’s picture.


##RF Can-tenna 

The goal for this component of the project was to build a directional antenna that is able to gather up MAC addresses within a certain area. The Cantenna that would have its signal reduced by adding attenuators until the signal reached a similar distance of the ping sensor.

During testing, it was identified that the wireless receivers continued to radiate a signal after having the antenna removed from it. Due to this issue, a confirmation of the MAC addresses being detected directly in front of the Cantenna was not guaranteed. Another issue that was encountered was that mobile devices do not continuously send packets to be discovered. The user of the mobile device has to manually open up an application that will send information through WIFI.

Due to the issues explained above, a decision was made to not use any attenuators on the Cantennas and to record the Received Signal Strength Indicator (RSSI) of the captured packets. The RSSI is used to determine the distance between the Cantenna and the mobile device. When the packets are picked up by each Cantenna the RSSI is checked on each WIFI monitor to determine if the four RSSIs are about the same. If the RSSIs recorded are of similar values then it can be inferred that the mobile device was in the center of the certain area chosen.


 
##Server    

###Sensing Box  

The Sensing Box part of the server subsystem, implemented on a NUC, acts as a hub for the other modules. Each Sensing box connects to a ping sensor, the RF Cantenna, and a camera (in our case an android phone). The Ping sensor communicates with the server through the sensing box. The RF Cantenna is used to detect MAC addresses which are sent to the server every second. The take picture command from the server is first passed to the sensing box, and then to the camera. Once the picture is taken, the picture is directly sent to the server via FTP, bypassing the sensing box, while communication between the sensing box and server is done via JSON.

###JSON Server  

The JSON Server is a multi threaded server that process all of the JSONobject commands sent to it from the other subsystems, and makes decisions based on these commands. It requires each thread opened to provide a device name for the client on the other side. This allows the logic and organization required by the server.  It waits for a command from a a sensing box, indicating that its ping sensor is activated, at which point it checks the latest MAC addresses to see if there is a MAC address detected on all four PBs. If so, it sends a command, through the sensing box, to the Facial Recognition Software to capture an image. Once the picture is taken, If a successful response is obtained from the Facial Recognition Software, the image and MAC address are then associated in an SQLite Database.  

## Facial Recognition

This module is designed on Android platform. The main functionality of this module is to take pictures in the background and process them to detect faces and also send the cropped face images to Server using FTP.  The process of developing this module involved the following steps.

**i) Creation of Face Detection Activity**

This is the main activity of this Android project. An OvenCV Camera View listener was implemented on it to handle OpenCV commands. The activity has two important methods that can be used to implement image processing. 

* `onCameraViewStarted()` method is automatically called when the camera is started and is utilized to initialize the OpenCV dependencies.
* `onCameraFrame()` method is called whenever a new frame is generated. This method is used to call the image processing method on the newly created frame to detect faces. The face detection was done using the Haar-Cascade classifier.

**ii) Communication with the Server**

 The communication between the server and OpenCV involves several JSON handshake messages back and forth in sequence. Each message has a `command` key that indicates the purpose of the message. The second key of each command conveys the information to Server/Android Client. The format of the JSON messages can be found in [Readme file](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Project2)

Following describes the communication flow between the Server and the Android device.  

* The Android device sends a JSON connect message to the server once it is ready to take commands.
* The Server sends a `Take Picture` command indicating the android device to start image acquisition and processing.      
* The Android device sends the file-name as reply message once it has completed the processing on the image.
* The Android device then sends the cropped image to the Server using a FTP connection. 

**iii) FTP Server/Client setup**

An FTP server program was written in Java that hosts a FTP server at the specified IP/Port, and also binds a directory of the local drive to be shared with its users. Similarly, an FTP client was written in java that connects and transfers the generated cropped images (faces) to the local drive of the Server. 

Both FTP client and the communication mechanisms were implemented as a separate AsyncTask and integrated with the Facial detection Activity. The program for this module can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Project2/Team1/FaceDetection/app/src/main/java/com/nagaraj/facedetection/DetectActivity.java) 

##Microcontroller  
Project 2 utilizes four ultrasonic ping sensors surrounding the detection area to determine if a person is standing in front of the camera. If all four ping sensors detect an object, the Android device's camera used for facial recognition takes a picture of the subject for face detection processing and MAC address association. The ping sensors are each controlled individually by a Teensy++ 2.0 microcontroller which communicate with the promiscuous box through a client Java application ran on a computer connected to the Teensy via a serial USB connection.   

The ping sensors have a usable range of approximately 12 feet (144 inches) with the most accurate range at approximately 86 inches or less. This range is where the ping sensor tends to deliver the most consistent readings. Using this range, the four ping sensors are each placed at a location on the perimeter of the detection area with an angle towards the floor where the floor will be at this maximum usable range. Each sensor is located at approximately 90 degree intervals around the detection area's perimeter.

![Ping Sensor Layout](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/td08/Project%20Images/ping.png)

The Teensy microcontroller runs a script that continuously fires the ping sensor and outputs the range reading of whatever object is in front of the sensor to its serial port. The Java application connects to the Teensy and receives the range data, in inches, over the USB serial connection. When the range reading falls below the 86 inch threshold, meaning a person is standing in the detection area, the Java application sends a trigger message embedded in a JSON object to the promiscuous box over a wireless socket connection. The promiscuous box then forwards the trigger to the main server indicating that a ping sensor has detected a person within the threshold area. When all four ping sensors send a trigger to the main server, the server sends a command to the Android device using the OpenCV library. The Android device's camera takes a picture of the subject in the detection area for facial recognition and MAC address association.

##Conclusion  
Project 2 introduced several new layers of hardware to interface with software at different levels of operation. While our team had previous experience using the external wifi cards to sniff MAC addresses, we had not yet experimented with directional antennas or manipulating an antennas range. The microcontroller component of the project also introduced opportunities to automate processes as well as operate sensors that can further aid in the detection of a person and their electronic devices. The cantennas used did provide a directional attenuation of other signals not located directly within the cantenna's range of operation, however we experienced some difficulties assimilating the cantenna with the external wifi card due to the card having a secondary internal wifi antenna that was not able to be bypassed effectively. These difficulties led to an alternative method of sniffing individual MAC addresses by associating the timestamp of the OpenCV snapshot of an individual with the timestamps of discovered MAC addresses in the detection area. Although the final solution was not as elegant or as efficient as originally designed, the teams were still able to accomplish facial recognition and MAC address detection using cantennas, additional ping sensors, and the OpenCV powered Android device. 