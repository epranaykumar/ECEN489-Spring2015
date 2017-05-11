# Project 2
## Team Members
* Yajie Zeng, yjzeng8833 - Collector
* Rong Liu, liur180855 - RF Antenna
* Anudeep Tungala, nranudeep1990 - Facial Recognition 
* Randy Neal, gmnealusn - Micro Controller

[Report Wiki page](https://github.com/CourseReps/ECEN489-Spring2015/wiki/Project-2-Team-3-Report)

## Critical Needs

**Collector**
* The goal for the server is performing MAC sniffing and send the command to facial recognition subsystem to take pictures of the person who is detected with his/her MAC address in certain area. 

**RF Antenna**
* The goal of RF Antenna team is to configuring the antenna to pin-point the exact location of the mac address within a given area. To do that, the gain of the antenna need to be reduced. Furthermore, cantenna will be used since the beam will be narrow.

**Facial Recognition**
* The goal of the facial recognition is to capture an image and crop the face of the person it detected from the image. This system will be turned on when it receives a command from the server to capture an image. The cropped image is stored in local folder before it begins transfer to the server over ftp.

**Micro Controller** "Teensy Board"
* The goal of the micro controller in our project is to detect the target in our RF zone and send a signal to the server via a Java program which will be used to activate the cameras.
* To physically detect the target, we will be using a sonar (ping) sensor that is directed toward the RF zone, and based on the distance to the zone, we will then be able to better isolate the target from any other people in the area.
* Once detected, the Teensy board will send a signal to the Java program with the distance to the target, which the Java program will use to generate the activation signal.

     ***Update***
    - The Ping Sensor is somewhat unreliable, it will only detect sometimes and the focal point is difficult to       locate.
    - Therefore, I have successfully built and tested a pressure switch that interfaces with the Teensy board.
    - The floor switch can be hidden under rug or floor mat and requires about 5-10 pounds to activate.
    - I plan to use this in conjunction with the Ping Sensor to reduce errors in detecting the target.

###Progress

**Collector**
* 

**RF Antenna**
* Update: several test are being done by using cantenna. However, the issue is that we are having a hard time picking up a specific device within the beam width. In addition, we are doing research on a power divider, attenuator, and cantenna.

**Facial Recognition**
* Update:
         Able to detect image, crop it and store it. Instead of using JNI to run C++ code we agreed upon accessing C++ code in .exe format from Java. Able to run the .exe from Java but facing some intermittent problems.

Update:
- Completed Facial Recognition using on PC cam
- Completed the boxing of the detected faces and display for view.
- Completed Cropping of the faces from main image and storing them with the incrementing names in desired   folder.
- Completed creating a Java Client that connects to a server and perform the agreed handshakes.
   Flow is as follows.
   - Send the command "connect" with device name
   - Receive the command "takepicture" with time stamp.
   - After Performing the Opencv stuff send back the "filename" command with filename or NULL
- Completed in creating a temporary server that creates server socket and connects to client with necessary handshakes.
- Created a FTP server with specified user name and password on required port.
- Completed a client functionality that transfers a file over FTP to server.
- Completed and tested functioning of Face  Recognition Client that communicates with the server
- Integrating with server guys.

**Micro Controller**    
- Completed ping sensor circuit and program (Complete 11 Mar 15).
- Built pressure plate and tested in conjunction with the ping sensor (Complete 23 Mar 15).
- Write Java program to interface the Teensy board circuit with the server (Complete 26 Mar 15).
- Interfaced Java program with Server to detect pings from Teensy board (Complete 26 Mar 15).
