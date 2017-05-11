




# Project 1:  Passive Device Monitoring







### Team 3 Members
Kevin Christopher Wilkens | The Notorious S.V.R.  
Hong Pan | PromiscuousBox  
Yajie Zeng | R2Data  
Evelyn Susanah Cifuentes | elCloudo  

## Abstract
This project focuses on identifying active Wi-Fi devices using a network of wireless sensing nodes. The sensors will collect network traffic meta data in monitor mode, also called promiscuous mode. Monitor mode enables a Wi-Fi network interface card to capture certain aspects of Wi-Fi packets in an area, regardless of their source or intended destination. Leveraging this information, it is possible to estimate useful parameters such as current population density, data traffic trends over a period of time, or most frequent visitors.
In this project our team was assigned the task of utilizing a device that would read packets of data and identify the Wi-Fi MAC addresses associated with these packets.  We would then store this information with respect to time in sqlite databases, and after storage we would implement an Android program that would interface with the device storing the network data, and receive new data from the storage device.  Next, we would implement a server that would interface with the android device to receive data, store it in a sqlite database, and parse the data into time frames with some population estimate calculations that can give an estimate of population density over time.  Finally, we would write a program that would display this information on Google Fusion Tables, a well organized cloud data representation engine that would allow us to present the data.  








## Introduction/Summary of the Idea
To execute this project, we broke the work up into 4 parts or modules.  Each person on our team was assigned to a specific module and worked with other members of the team whose modules would interface with their own.  One person would work on the the computers employed for data collection; the Next Unit of Computing (NUC) by Intel. The NUCs were picked because they have sufficient power to perform the necessary operations, and they possess a small form-factor.  This module was classified as The Promiscuous Box, or PB.  Another person worked on the Android device used for transmitting data, which was classified as R2DATA.  A third team member worked on the Linux server being used for receiving information from R2DATA and for converting the information to presentable data for the fusion tables, which was classified as The Notorious S.V.R.  Finally, one team member would work with the team member in charge of The Notorious S.V.R. to focus on presenting the data using Google Fusion Tables in an organized and professional fashion.  This final Google Fusion Tables Module will be named El Cloudo.

Design Flow for Project








### PromiscuousBox
The first function of Promiscuous Box is to capture the packets and extract the MAC address information when connected to a wireless network. TP-LINK TL-WN722N network interface card is used in Ubuntu 14.04 system to accomplish the first function. The network card is configured into promiscuous mode in order to sniff all the packets in the network. Java does not have any access to the DataLink layer where the MAC address resides, however,  there are two Java libraries that wraps around LibPCAP C library and allow user to use most of the functions in LibPCAP library. The two Java wrapper libraries are JnetPCap and JPcap. For this project, JnetPcap is used. To set up JnetPcap correctly, it is required to have LibPCAP C library installed in the system. A full documentation is available on jnetpcap.com. 

**A typical workflow for JnetPcap is as following:**

1. Setup an error buffer. 

2. Get a list of all available interfaces and pick one (see Pcap.findAllDevs()

3. Open either a live network interface, discovered in previous step, or open an offline, a capture file (see Pcap.openLive() or Pcap.openOffline())

4. Read either one packet at a time (Pcap.nextEx()) or setup a dispatch loop (see Pcap.loop() or Pcap.dispatch())

5.  If using a dispatch loop, wait in your callback method (see PcapPacketHandler.nextPacket()) and receive incoming packets.

6.  Once you have received a packet, typically the packet is either processed on the spot or put on a queue that is read by another thread

7. When the dispatch loop exists due to either an interruption (see Pcap.breakLoop())or simply the requested number of packets at the time the loop was setup, then process the queue if it hasn't been handed off or cleanup

8. Always the last step is to close the pcap handle (see Pcap.close()) to allow Pcap to release all its resources held

An “Ethernet” object is set up to extract the MAC addresses in the PcapPacketHandler. The method “netPacket” is called when a packet is captured. To extract the MAC, “Ethernet.source()” and “Ethernet.destination()” can be called to get the MAC addresses. The returned MAC is formatted into Human readable format using FormatUtils.mac() method provided by JnetPcaP. To get unique MAC addresses in one second time frame, all the MAC addresses that are captured in one second are dumped into Java Hashset. Because HashSet does not allow duplicate entries, it will only keep uniques MAC addresses. The set up one second frame, Java Instant object is called twice to record the time when the first packet in the one second frame is captured, new time instant is recorded and compared to the first time instant to check if one second has been reached.
The second task for Promiscuous Box is to get all the MAC address into a sqlite database, so that it can be transferred over the bluetooth connection. A time stamp from the captured packet is also recorded into database.  It is recommended to use sqlite-jdbc-3.7.2 if the operating system is linux based. An repetitive “insert data” call to the database is used to record all the MAC address, then the PB server (discussed by R2Data) copies the database created by the network packet capturing program in a time frame set up by the programmer.


### R2Data


R2Data will be receiving database file from PB Server.  R2Data have the “hand shaking” process with both PB Box and the notorious server. It receives the first version(full version) from PB Box, and send it to Server. When the server received the file, it will send the last entry’s ID number to R2Data, since ID number is unique to each entries. When the app is trying to load the new database file again from PB box, it will generate a new database file with updated mac address entries according to the ID number which server send back to us. And R2Data will send the updated database to the server and the new database will be merged to the last one the server received. 
In order to implement the interfaces with PB Box. It is necessary to get the bluetooth communication between the PB box and android app. We send the database file as the string using the socket. The code for the server is integrated to the Box side and we use an app on android to communicate. The other interface it is dealing with is the WIFI communication for the app and the server. To implement this process, a server socket is created in the server and waiting for Android app to connect to it. Anf file input streams and output strings will be used to transfer the database file from the android app to the server. 

### The Notorious S.V.R.
The Notorious SVR will be receiving .db files from R2DATA. The databases will be named with a timestamp based on when R2DATA pulled the data from the PB, and the SVR will parse the databases received from R2DATA into a single organized database. There will be two tables in each .db file received from R2DATA, one table will include only the PBID of the box that the data is pulled from, and the other table will include an ID associated with each entry into the database on the PB, the MAC address, and date/time stamp info. The final SQLite database on the SVR will have 4 columns, one with each ID read from the PB, one column with date/time stamp, one column with the MAC address, and another column with a PBID that tells which promiscuous box the ID, date/time, and MAC address came from.  

In order to implement all of the tasks performed by the SVR, it is necessary to first be able to interface with R2DATA using Wi-Fi sockets in Java, and successfully send files from the phone to the server.  To do this, a simple server socket can be created on the SVR that will allow an Android client to connect to it.  Next use file input streams and output streams to copy the file received from the Android device, and add the file to a directory on the SVR.  After doing this we can pull the data from this temporary database and add it to our main database, and once this is finished and confirmed we can delete the temporary database off of the server.  


To merge the temporary file with our main database, we implement a java class in our file receiving program called dbMerge.  This reads the information from the temporary database using SQLite commands that are implemented within a while-loop that uses logic to end the loop after reaching the end of the data in the temporary database.  
Next, the database merging class will read new information from the main database, and check for any duplicate data.  If there are any duplicates based on time and mac address information, all of these duplicates will be removed from the database.  Then the program will take all of the new information and create an additional database that will provide some analysis of the data.  This new database will include a single table including intervals of time defined in the dbMerge class, and will also include number of mac addresses in each time period, an estimated number of people based on those mac addresses entered into a linear regression formula, the PB the data was received from, and a final column stating whether the information has been sent to the Fusion Table, elCloudo.  Finally, this analysis database will be given to elCloudo.

### elCloudo

elCloudo is the final step of the project, where all the collected and filtered data is ready to be stored and displayed. After the data has been collected from the database and stored into the Google Fusion Tables, it can easily be displayed as a chart or graph for quick analyzation of the data.
The idea was to design elCloudo to run through an existing database and read the content from a specified table ,row by row. That data would then be pushed, row by row, onto the Google Fusion Tables. 
elCloudo was created in two steps. The first application was written to get OAuth2.0 working and simply wrote strings of information specified in the Java code to a row on a Fusion Table. The method created to write a string to the Fusion Table is seen in the figure below. OAuth2.0 was a very important part of getting the the application to work, because it is what allowed the application to write to the Fusion Tables. First, the OAuth2.0 credentials were obtained from the Google Developers Console so that the application could request an access token from the Google Authorization Server, extracting a token from the response and sending the token to the Fusion Tables API. The credentials, which contains the Client ID and Client Secret, were downloaded from the Google Developers Console as a JSON file and placed inside the project. 

Figure 1.
![image](https://scontent-dfw.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/11050196_968710696473844_7244069232712624037_n.jpg?oh=df5c950e6f1f24e968bf6d1fcd623c75&oe=558F9279)
As soon as everything worked with the first application, a new application that would pull information from a database was begun. This application did not need any new libraries or anything seperate installed. All that had to be done was establish a connection with the database in question and call the information from the table with SQL commands. For example, when retrieving information from our table named Data within the MainAnalysis database, we used the following line:

Figure 2.
With some work, both applications were then combined in order to be able to pull the database’s information and send it into the Fusion Table like the string was sent in figure 1. The method that was established to insert the data into the Fusion Tables can be seen in the figure below.

Figure 3.
![image](https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/10484970_968710703140510_6520895731960434069_n.jpg?oh=deff37c667075f8d7f277985da05906d&oe=55866EF2&__gda__=1435297347_181e293fc01cd53b2d7122a06710f8c3)
To test the code, sample database tables were created to pull information from and make sure it made it’s way into the Google Fusion Tables. There was never an actual problem until we tried using the actual data we collected, that was much bigger than a couple of rows and the application produced a “Exceeded Rate Limit” error. The was most likely due to the Quotas the Google Developers Console has in place. For the Fusion Table API, the per user limit is 2 requests/second/user. To fix this, a delay of about 4 seconds was placed within the while loop shown in figure 2 and within the insertData method shown in figure 3. This slows down the process of adding each row onto the Fusion Table, however, it makes it to where it is able to add all the information without reaching the rate limit.
Evaluation of Data Results
The Promiscuous box collected data for two days at the Wisenbaker building. The amount collected was about 65Mb, which is a rather large amount of data. The information was then filtered to remove duplicate MAC addresses and an estimate amount of people based on the number of MAC addresses in each row was added to the table. Beginning with about 16,000 rows, after all was completed, there remained a total of 559 rows in the database table. This provided us with a good amount of information that would keep the data accurate, but not an overwhelming amount of information that would make the complete process a slow one. An hour and a half later, when all 559 rows were successfully pushed into the Google Fusion Table named “DATA”, a good representation of the activity within the building during the span of those two days was able to be seen. Of course, not all the data was pulled from the S.V.R. database table. The S.V.R. database table was created in the format seen in figure  4 while the Fusion Tables were created to include only information we wish to analyze from the database table, and its format can be seen in figure 5. 


![image](https://scontent-dfw.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/10994323_968710663140514_3137289040458093314_n.jpg?oh=35e02eb736ae6367f18ccfb632f2527d&oe=558EC6B4)
Figure 4. -S.V.R. Database format


![](https://scontent-dfw.xx.fbcdn.net/hphotos-xpa1/v/l/t1.0-9/10407378_968710666473847_1495694222856177747_n.jpg?oh=8ae964aa8926736c92b8e8027e853853&oe=5577D8A4)
Figure 5 -Fusion Table format

The graph resulting from the data we collected over the span of those two days can be seen below in the figure 6. Although there are a few random jumps in numbers of people, it is a good representation of the amount of people that were present every 5 minutes. The graph clearly shows the decrease in people during the earlier hours of the day, around 1am to 4am. You can tell where the day beings on the table by the marking of the day. Between the times of 9am and the evening, the amount of people present clearly increases and continues to change as people come and go from the Wisenbaker building.



![image](https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xap1/v/t1.0-9/10363673_968710676473846_7078397562488904424_n.jpg?oh=c3f9e45ec6511c92641f542b820f066d&oe=557F1BD0&__gda__=1433878028_38dea789a9134b3c59f9bb2cf9a6ecba)
Figure 6.
## Conclusion
After the project was completed, we got a good amount of data and we were able to get a clear representation of the movement of people within the Wisenbaker building. Apart from the data, we all got a good understanding of how to create this program. This project is our first team project for this class. It is a good opportunity to get us involved and actually learn something from hands-on experience. It not only tells us how to collaborate with team members, but also give us opportunity to learn from each other. However, since it is the first project and not everyone has the same programming skill set. It was really hard for everybody keep in the same pace. But it is a good chance to learn new things. 
