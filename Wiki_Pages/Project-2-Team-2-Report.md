##Introduction
The goal of this project is to utilize directional antennas to read mac addresses within a more focused, controlled location than the network cards from project 1, and to match the captured address to a person’s face.  This can give a more accurate estimate of an area’s population, and track the number of times a single person walks through the area within a chosen time period by matching their device’s mac address to their face.  

This project builds on Project 1. Four sensing stations are set up to focus on a central area. When a MAC is sensed in this overlapped area, a picture is taken using a camera next to an attention-getter. This system links a person’s image to their mac address.

![](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/jacano23/Design.jpg)

## Server & Communication Handlers
The server side of this project is a multi-threaded server-client program that acts as a transmission medium between the Micro-controller, an Android Device, NUC and the Main Server. 

##### The NUC (Promiscuous Box)
The PB collects MAC addresses and RSSI and sends them to the Main Server to be stored in a database.

##### Handshaking between interfaces
JSON objects are utilized as the interface's handshaking token for this project. The library API used is  org.json.simple and to parse the JSON Object, a user created function tokenizes the object into a hashmap of strings.

##### Communication Protocol 
The sensor communication handler waits for a JSON object to be sent from the micro-controller through a socket. When it receives the command: Ping, it immediately sends a JSON object to the Main Server which processes the command and checks the latest MAC address entry from all 4 PBs. If there is a unique MAC address shared by all 4 PBs, the Main Server sends back a JSON object called takePicture to the server communication handler. This directs the CV handler to communicate to the android device to take a picture. The OpenCV app on the device then sends a JSON object called command: fileName to the server handler which sends the command to the Main Server in order to inform it that the image taken is going to be transferred through a FTP connection. 

The Main Server receives the image from the FTP socket and stores it into a sqlite database with additional information such as the time in milliseconds when the image was taken, the associated MAC address, filename of the image, and RSSI.

## RF Antenna  
The RF component of the project is responsible for gathering all mac addresses within a very limited area with the use of a directional antenna. The antenna chosen is a cantenna that has its signal reduced with the use of attenuators until it matches the same distance of the ping sensor. 

Results during testing shows that the wireless receivers purchased and used during the first project continue to radiate a signal after having its antenna removed. One of the wireless receivers contains an internal antenna and still radiates with it removed. Confirmation that the mac addresses being picked up by the software is no longer guaranteed to be from directly in front of the cantenna due to these findings. Issues during testing include the discovery that mobile devices were not guaranteed to be continuously sending packets that can be easily discovered. An internet based app needs to be accessed to send packet information with reliable results. This reduces the chances of finding a device despite being within a given region as required by the initial guidelines. 

Attenuators are no longer needed on the cantennas due to these findings, and the alternative is to record the base RSSI values measured in the software program when a packet was sent. The relation between RSSI strength and given distance is used to determine a range of RSSI values to find a device within a given area. The cantenna effectively shows higher RSSI values in front of the opening than from the side, correctly showcasing its abilities as a directional antenna. For testing, the code used is a modified version of the wireless packet sniffing program used in both project 1 and 2 designed to look for a single person.

##Microcontroller
The purpose of using the microcontroller for this project is to create a ping sensor that sends an alert out when someone walks into the area. The microcontroller used for this project is the Teensy 2.0++ which uses the Arduino IDE and is compatible with most of Arduino’s libraries. Pictured below is the Parallax Ping Sensor being used and the Teensy2.0++.

![](http://i.imgur.com/q7D7qdu.jpg)

The ping sensor is a device that measures how far away an object is. The way it measures distance is by measuring the time it takes, in microseconds, for the echo of the sound that the ultrasonic speaker emits to return to the microphone. The time is then converted to inches.

We found that the maximum distance that the ping sensor can detect is 145in, however, in order for the person to be accurately detected, 80in would be preferred as ping’s sensing region. The ping’s readings can be read from the Arduino’s Serial Monitor, however, for this project we needed to interface the information being read by the Teensy with a Java program so the alert could be sent to the server. We used the Arduino’s serial ports for java to read the information from the Teensy. The RxTx library was used in order to make the serial communication possible between Arduino and Java.

After being able to read the distance in the Java program, the code was developed in order to communicate with a server via an IP address and only send an alert when the distance read by the ping sensor was in range. For the project, we set up the ping sensors in an ‘X’ shape pointing toward a center at about 80 inches from the sensors. The range for the triggered alert is for the distance of the person walking in to be to less than or equal to 96 inches. This way, the sensors are sure to only be triggered when someone walks into the center. What the final Java code does is that it reads the ping in when the sensor detects someone in its line of vision and sends a JSON object to the collector on the NUC which communicates the Ping command to the server. The server then sends an OpenCV command out for the picture to be taken.

##OpenCV Face Detection - Android Device

![image](http://mymobilerobots.com/myblog/wp-content/uploads/2013/02/tutorial-opencv-2-4-3-face-tracking-detection-video-capture-console-using-vs-2010-c.png)  

For Team 2, the OpenCV portion of the project involved using an android device to process commands from the server, utilize the camera on the device to recognize a person’s face and then take a picture of that person.     
	
####Recognizing Faces in OpenCV   
The android application uses an OpenCV camera view provided by the OpenCV Android SDK, and looks at various features of the images captured within the camera view.  The OpenCV website includes plenty of helpful documentation and instructions with how to implement their software in a number of programming applications.  The application then compares these features to OpenCV facial recognition raw data (in our case we used the haarcascade_frontalface_default.xml library file).  If the comparison between the image and the raw data library indicate that there is a person's face in the image, the camera view will show a bright green box around the person’s face indicating that it is, in fact, a human face.   

 
After taking the person’s picture the android device will send the picture back to the server, using the method described in the server section, for it to be processed and matched with a mac address corresponding to that person.  Then the process is complete and we are ready to continue receiving commands from the server.  

In the end we were able to get this entire portion of the project to function as intended.  All project code for the OpenCV portion of the project can be found in our project 2 team 2 folder in Github. 

#Conclusion
This project provides valuable learning in the fields of hardware and software integration, face detection, and directional antennas. Despite temporary complications on the antenna side, the overall project deliverables open many doors to different ideas and projects.