#Scope   

The goal of this project is to utilize directional antennas to read mac addresses within a more focused, controlled location, and to match the captured address to a person’s face. Doing this can not only give a more accurate estimate of an area’s population, but it can also track the number of times a single person walks through the area within a chosen time period by matching their device’s mac address to their face.

This project will be build on top of Project 1 using statistics/ analytics of MACs. Four sensing stations will be set up to focus on a central area. When a MAC address is sensed in this overlapped area, a picture will be taken using a camera next to an attention-getter, namely a TV. This system will try to link a person to the mac address and possibly the image.

#Components of Project  
#### Microcontroller  

The purpose of using the microcontroller for this project is to create a ping sensor that will send an alert out when someone walks into the specified area. The microcontroller used for this project is the Teensy 2.0++ which uses the Arduino IDE and is compatible with most of Arduino’s libraries. Pictured below is the Parallax Ping Sensor being used and the Teensy2.0++.
#### The Notorious S.V.R.  

The linux Server is organized by utilizing three different clients (collectors) that connect to the micro controller, the android device, and the server itself. There is a master class that starts three different threads with each thread opening either a ServerSocket or a Socket.

#### Facial Recognition-OpenCV   

For Team 2, the OpenCV portion of the project involved using an android device to process commands from the server, utilize the camera on the device to recognize a person’s face and then take a picture of that person. After taking the person’s picture, the android device will send the picture back to the server for it to be processed and matched with a mac address corresponding to that person.

#### RF Antenna  

The goal of this is to focus the antenna to read MAC addresses in a specific direction as opposed to reading MAC addresses from a radius around the network card.  This will be used in conjunction with antennas from other teams to create an overlap area so that we can see MAC addresses in a given area.
