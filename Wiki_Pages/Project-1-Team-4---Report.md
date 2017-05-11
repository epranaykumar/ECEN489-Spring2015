# Project 1 Overview
The goal of this project was to use a WIFI card running in promiscuous mode to collect the MAC addresses of all devices using a network and using that data to estimate the amount of people in the area, then use an Android device to collect and send the data to a master server, and then upload that to a Google Fusion Table. This was accomplished by using four different subsystems: The Promiscuous Box (PB) which is a mini PC (NUC) that collects the MAC addresses, an Android device (R2Data) that collects data from the PB via Bluetooth and sends it to the server via WiFi, the server (The Notorious S.V.R.) which takes the data from R2Data, manipulates it, and sends it to the cloud, and finally the cloud (El Cloudo), which displays the data using Google Fusion Tables.

More in-depth descriptions of each of these subsystems are given below; the WIKI used during the development of this project can be found here: [Project 1 Team 4 Wiki Page](https://github.com/CourseReps/ECEN489-Spring2015/wiki/Project-1-Team-4)



## The Promiscuous Box
The Promiscuous Box (PB) is the work horse for collecting all of the data. The BP is split into three separate sections; the wireless sniffer, the parser and filter, and the bluetooth connection. 

**Wireless Sniffer**

The wireless sniffer is the first component that generates all data. The purpose of the sniffer is to activate the promiscuous mode on the external antenna plugged into the Intel NUC and to scan the area to record all MAC addresses with a timestamp. The code for this project used by team 4 was developed by George Sullivan from Team 2. The code for his sniffer is written in c and can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Project1/Team2/PromiscuousBox/8.wireless_mac_sniffing.c). It generates a csv file up to 100,000 entries with the data organized by timestamp in S, timestamp in uS, Source MAC, and Destination MAC.

**Parser and Filter**

The majority of the processing power is used in the parser and filter. This program, written in Java, runs simultaneously with the sniffer with a buffer. The buffer makes sure that no .csv is being accessed and written into a SQLite database unless there is an additional .csv available for the sniffer to write into. The program requires an additional .jar file to work with SQLite. The preferred option for this .jar chosen to be universal across all platforms in the project was JDBC. After initializing the table, data needs to be inserted into the table. For this project, the only data needed from the wireless sniffer are the timestamp in S and the Source MAC. Due to the high flow of information and the average speed of a human, there is no need to check the accuracy of a person in an area to within a millisecond. We also only care about the actual people within the area, so their destination MAC addresses are ignored.

To important and read the information from the .csv files the option that team 4 found most efficient was to use a buffered reader and a line split command that splits using commas. Below is an example of such a line splitter. This splits the line being read into an array of strings.

    String[] values = line.split(",");

Logic is applied to the resulting data and all MAC addresses are stored into a hashmap. Hashmaps will remove any duplicate entries, so this filters out all non-unique MAC addresses within one second.

**Bluetooth Communication**

After all .csv files are filtered into the database, the database can be used by the bluetooth communication part to send the database to the android device when it is within the area.  More information on how this connection is created and the socket architecture is explained in the next section.

## R2Data
The purpose of R2Data is to periodically gather the databases from any or all of the four PB's and then shuttle those database files to the Notorious SVR. This is accomplished using three key components: a simple PB server, the Android application, and the Notorious S.V.R. receiver server.
#####Simple PB Server
After the .csv files have been collected by the PB, the primary database is copied locally by the simple PB server program. The main idea behind this program is to allow the primary database to be continually accessed without having to stop the primary PB program. After the new local database copy has been created, the entries are compared to the first data check handshake; a column in the database establishes a unique row id number that never repeats and is constant to that particular MAC/Time. This value is pulled from the SVR and all values before that value are erased from the local database copy, thus making subsequent file transfers faster while leaving the main database untouched. This local database is then transferred byte-wise using Bluetooth radio using a file streams. This is very similar to using standard WiFi sockets, with the exception of needing certain Bluetooth imports and associated JARS from Bluecove. For Linux 64, both Bluecove 64 bit 2.1.1 snapshot and Bluecove gpl 2.1.1 snapshot are needed to import the appropriate libraries into Java. The simple server code is also designed to run continuously and independently of the main PB program and allows multiple R2Data devices to retrieve the databases simultaneously. 

#####R2Data Android App.
The next piece of the system is the actual Android application. The concept behind R2Data is to simply act as middle man between the PB and the SVR, and therefore not manipulate the database files whatsoever. Again the file is gathered from the PB using Bluetooth sockets and file streams; after the file transfer, the database is held in the downloads root directory in the Android file system (this is very important, as many Android devices are hard coded to only allow file transfers to and from this folder only and can cause the app to fail). The application is designed to have two parts, one is the PB data transfer side and the other is the WiFi S.V.R. side. The Bluetooth part utilizes a spinner (drop down menu) that lists the PB id, this is then matched up to the Bluetooth MAC address in a for-loop inside the spinner listener. After selecting the right PB, the user simply presses the Load Data From button. This makes the gathering task more user friendly. The WiFi portion of the App utilizes two simple text fields where the user will enter the IP address and Socket number for the S.V.R. and then press the Transfer button to send the database files over to the S.V.R. On the WiFi side, the app looks through the directory and sends all files that have the form PB“#”.db to the S.V.R., whereas in the Bluetooth part only one file is transmitted from the PB to the app. 
#####Notorious S.V.R.
The S.V.R. will be discussed further in the next section, however the data handshakes are noted here. In order to facilitate expedient file transfers and eliminate redundant data in the transfer process, the last ID number that was transmitted to the S.V.R. is retrieved from the database file that the app sent and is recorded into a local database on the Android device, this database is only used by R2Data to store the handshake information. This ID is used to initiate the previously mentioned handshake between the PB and R2Data. 



## The Notorious S.V.R
The Notorious S.V.R acts as the data collection point for all of the data collected by the PBs is responsible for processing the data so it is suitable to load into the fusion tables. The server runs using 2 threads: a connection/windowing thread and a linear regression thread. The data is transmitted from an R2Data using a WIFI socket. 

The data received from R2Data was processed to generate information that is suitable to be pushed to the Fusion Tables. The processing involved two steps.

**Server Connection**

The server connection thread will be constantly running, waiting for an android device to connect to the socket. Once the device connects a database file will be read in over the socket and the two tables in the read database file are merged into a new database file that contains all of the data in one table. After the windowing is completed the thread will return to waiting for an android device to connect through the socket.

**Windowing**

Windowing is then done on the new database file by finding the number of unique MAC addresses inside of a specified time window. The number of unique MAC addresses, along with the last time in each window is then pushed into a new, final database. Once the final database has been finished the connection thread will begin waiting for a new connection while the linear regression thread will pull the new data from the final database and push it to the fusion tables. 

**Linear Regression**

After windowing, the data is passed through the Linear Regression block that estimates the number of people per window based on the training set that is inputted along with the windowed data. The training set comprises of an array of (Number of MACs, Number of people) pairs that is collected from the same/similar environment to that of the testing environment for a sufficiently small duration.  
The Linear Regression block was implemented in Java using the Efficient Java Matrix Library. The linear regression is designed based on Least Squares (LS) approximation and it estimates a best fit for the coefficients (a,b) in `(# of People)= a*(# of MACs) + b`. First, the training set is passed through the linear estimator to get  the coefficients (a,b) and this is used by the testing set to estimate the number of people.

## El Cloudo
The prime functionality of El Cloudo is to push all data generated by the Notorious S.V.R. to Google Fusion Tables and visualize the data on charts. The process of developing the El Cloudo involved following five steps.

**Creating and configuring a project in** [Google Developer Console](https://console.developers.google.com)

A project was created in Google Developer Console and the Fusion Tables API was enabled. The handshaking between the Client application and the Google Fusion Tables was done through OAuth 2.0 authentication procedure. So, the credentials (client secrets, Client ID, Redirect URIS) for a new Client application was created to provide access to the fusion tables and the JSON object containing the Client secrets was downloaded.  

**Building a Java application to get access to the Google Drive using OAuth 2.0 authentication procedure**

The Java application uses the Client secrets to connect to the Google drive of the user who logged-in through the Internet browser. The application connects to the user's Google Drive using OAuth 2.0 authentication procedure. The Java application uses [com.google.api.client] (https://code.google.com/p/google-api-java-client/),  [com.google.api.services.fusiontables](https://developers.google.com/resources/api-libraries/documentation/fusiontables/v2/java/latest/com/google/api/services/fusiontables/package-tree.html) and [Apache Tomcat Servelet API](http://tomcat.apache.org/tomcat-7.0-doc/servletapi/) libraries to accomplish this.

**Creating a fusion table using the Java application and inserting records generated by Notorious S.V.R.**

Using the Fusion Tables API, queries to create a table was executed and the table properties were also set using the API. The El Cloudo java application was created as a thread on the Notorious S.V.R and it keeps checking if there are new entries generated (based on the 'ADDED' flag in the database). If a new entry is generated, the application automatically queries to add them to fusion tables and changes the 'ADDED' flag to 'YES'.
The Google Fusion tables has a limitation on the number of queries that it can process within a second using the API. It was noted that it was not able to process more than 30 records at a time. So, the java application was incorporated with suitable delays to match the rate that the Fusion Tables can successfully handle.  

**Generating and publishing the Charts to visualize the data in Fusion Tables**

Charts were created for each PBID in the Fusion Tables to generate Number of MACs vs Time plots. The charts were then published to get iframes that can be inserted and formatted in HTML pages. 

**Creating a webpage (with periodic auto refresh capability) to display the published charts**

It was noted that the charts generated by the Google Fusion tables do not have auto refresh capability. So, the published iframes were imported into a HTML page and JavaScript was used to refresh the iframes periodically to give the user a real time experience.

## Conclusion

The project functions properly as a whole. The database collects and dumps all MAC addresses correctly into the SQLite database. There is a small bug in the code where the last time stamp in the .csv is deleted before it gets dumped into the SQLite database. That means the last second out of every 200 seconds is not included. However, The time stamp is usually recovered effectively from still having entries existing in the beginning of the next csv file. On the bluetooth connection, the primary problem found is that every once in a while a full database is not transferred from the PB to the android device. This is evident when the number of bytes don't match on both screens. The problem is rectified by reattempting to download the database again. On occasion, the PBServer code will occasionally fail to connect and label the SQLite database as locked. This also has a temporary fix of just running it again. After on the android device, it transfers smoothly to the server. From the android to server, the only concerns so far have been incorrect table names and the occasional corrupted database when pulled from the PB. The table names were fixed and is no longer an issue. The fusion table displays all information with an updated linear estimation.

After processing the data collected from the Promiscuous Box we are able to approximate the amount of people in the area around the PB from the amount of unique MAC addresses collected. The number of people is just a rough estimate but this project acts as a proof of concept showing that the method is viable. Future iterations of the project could include adding different antennas to limit the range from which the addresses are collected. The linear regression could also be optimized using a better training set.