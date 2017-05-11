##INTRODUCTION  
The goal for Project 1 was to collect the MAC addresses of all devices using a network in a certain area. This data was collected by using a WIFI card in monitor mode and once the data is collected it is stored using an SQL database. Then we used this data to get an estimate of the amount of people in the area. This project was accomplished by using four different components called: “The Promiscuous Box” which is a Linux NUC in monitor mode, “R2Data” which is an Android application designed to be a data mule, “The Notorious S.V.R” which is a server where the data is stored and analyzed and then pushed to “El Cloudo” which is a Google Fusion Table that displays the data analysis. The four components of this project are in more detail in the report below.
##PROMISCUOUS BOX
The Promiscuous Box, also know as PB or PromBox, is wireless sensing node implemented on an Intel NUC. The goal of this sensing node was to collect MAC address of active WiFi devices within the range of the PB. The Promiscuous Box was implemented using the following design components: Operating System, Wifi Card, Packet Sniffer, CSV Parser, and Bluetooth Server. 

###Operating System  
The operating system implemented on the NUC was Ubuntu Ubuntu 14.04.2 LTS. This OS was selected for several reasons. Ubuntu is free, has a low deployment barrier, and offers great support. It also comes packaged with the drivers required for our slected Wifi Card. Because it is Linux based, all of the libraries needed for implementation were available, with most of them obtained easily through the Ubuntu Software Center.   

###Wifi Card
The Wifi Card was a critical component of the Promiscuous Box design. The selected WiFi card had to support monitor mode, and be compatible with our selected OS and the NUC. Monitor mode is a state in which instead of connecting to a network, a WiFi card is repurposed to collect WiFi packets in its range. This is done passively, without affecting or alerting the packets themselves. The card selected to meet these requirements was the TP-LINK Model: TL-WN722N. 

###Packet Sniffer
Once our Wifi Card passively collects a packet, it must be processed to obtain useful information. This was done by short a C program implementing the PCAP library. This library/program gained access to the Wifi Card, placed it in monitor mode, began collecting packets, and stored the MAC addresses in a CSV file. The program implemented in our PB was developed by George Sullivan (https://github.com/I-Love-Github) with some light modifications for our own purposes. 

###CSV Parser
Once the Packet Sniffer had created CSV files of collected MAC addresses, these CSV files had to be parsed and placed in a database. A Java program was designed to run simultaneously with the Packet Sniffer to accomplish this. The Java program had two main components: The FileBuffer and the Parser.

####FileBuffer
 The FileBuffer is exactly what it sounds like. With the Packet sniffer constantly creating new CSV files, the older files needed to be processed and deleted to prevent redundancy of data. However, the latest file that was being used by the Packet Sniffer could not be deleted. To account for this, the FileBuffer was designed to take the oldest CSV Files, pass them to the parser, and then delete them on completion, while retaining a buffer of at least one other file to prevent deleting the Sniffer’s current CSV file. 

####Parser
Because of the huge amount of data being processed, the Parser had to be efficient. The parser also needed to remove any useless data to cut down on the database size. In order to accomplish efficiency, the CSV parser used a mapping of MAC addresses to collections of timestamps. This collection was chosen to be a HashSet to prevent duplicates without searching. In order to remove any useless data, the while creating the map, each entry was compared to a map of known MAC addresses and companies. If the MAC address wasn’t known, it was never added to the map. Once the entire file was parsed, the mapped information was added to a SQLite database, and the map and CSV file were deleted. 

###Bluetooth Server
The PB transferred the created database file to an android phone via Bluetooth. This will be discussed in more detail in the R2Data Section. 



##R2DATA  
The name of the android application created for project 1 is called R2Data. This purpose for this application was to be a data mule for the PB Box and The Notorious SVR. Once the PB Box created a database file, someone with R2Data installed on their android device can walk by the PB Box and connect to it via Bluetooth technology and begin to obtain a copy of the main database file via file transfer. The R2Data group decided to use a file transfer method so that the integrity of the data within the database would not be compromised. On the actual application GUI, the user can choose to obtain a database file from any one of the four PB Boxes by a drop down menu that contains the mac address of each PB Box. After choosing the correct MAC address the user needs to click the “LOAD DATA” button. This button then connects to the Bluetooth socket that is created by the PB Box. The PB Box first creates a copy of the current database and begins to transfer this copy by the byte size of the copy created. Then a file path is created so that the copied database file is then placed in the Downloads folder of the android device. For certain devices, certain files paths will not work. For example, in the earlier stages of R2Data I had the file path transfer the database file in to the Documents folder of my HTC device but the file would not transfer over. Now that the DB file has been transferred over to R2Data we will begin the process to transfer the DB file over to The Notorious SVR. The interface between R2Data and The Notorious SVR is a Client-Server connection through WIFI. The user then chooses which DB file that he or she desires to transfer. Then the IP address and Port number of The Notorious SVR need to be entered in to the text boxes so that R2Data can create a socket connection for SVR when the “Transfer” button is . Once the DB file is transferred over the SVR will send over the last Row ID of the database file that was transferred and we will store it on a background database using SQLite and Java Database Connectivity. This database contains a column for the PBID and a column for the last Row ID to keep track of the last entry of the main database that was sent to The Notorious SVR. This is so that we send the number of the last Row ID to the PB Box to make a copy of only the new entries of the main database. Then the process can start over to send more data base files from multiple PB Boxes.
##NOTORIOUS S.V.R
The purpose of the Notorious Server is to 
* Receive Sqlite database files collected by the R2Data mule and merge them into a single Master Sqlite database.  
* Perform a duplicate check to ensure that there are no duplicates with same Time stamp, MacAddress and PBID in the Master database file.  
* Perform windowing on the Master database to determine the number of unique Mac Address over the window length, for each PBID.   
* Estimate the number of people at a particular time point from the number of unique MacAddress at that time using the linear relationship estimated by the Least Squares Method.  

[The program for the Notorious Server can be found here ](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/epranaykumar/Project1/NotoriousServer)  

To achieve above, we used two threads in the notorious server namely 1. **MuleData thread**  for  receiving  the Sqlite database files and 2. **Merge window thread** for performing duplicate checking, merging, windowing and estimating the number of people. The implementation of these threads involves the following steps. 

#### Interface  
The interface used for receiving the files between the R2Data mule and Notorious server was a **file transferring interface over a wifi socket**.
####Duplicates Checking
The duplicate checking was performed using the following logic.   
1. Get the received ID from the table 'PBID' in the received database and for that received ID, calculate the last time stamp in the master database.  
2. Get the first Timestamp from the table 'DATA' in the received database and compare it with last Timestamp in the Master database and make the following decisions  
`if(FirstTSreceived > lastTS)`  
`merge  `  
`elseif(FirstTSreceived < lastTS)  `  
`delete entries in received database less than lastTS and `
`for TS equal to lastTS, delete duplicate mac address ` 


####Merging  
After removing the duplicates, add the column PBID to the table DATA in the recieved database and merge it to the Master database. The sample syntax is as follows.   
`receivedstmt.executeUpdate("ALTER TABLE DATA ADD COLUMN PBID INT");`  
`receivedstmt.executeUpdate("UPDATE DATA SET PBID = "+String.valueOf(receivedID)+" WHERE ID >=" + String.valueOf    
(receivedFirstID)); `

####Windowing
The window length for windowing is chosen so that it can capture the moving people in the sensing range of the PB box. For this project, **we have used 10s as the window length**. After merging, the windowing was performed using the following logic  
1. Get the Last Timestamp from the windowed database for the received PBID. [The format for the database after windowing can be found here](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Project1/SVR_Table_structure.png)  
2. Compare the Last Timestamp in the windowed database and in the Master database after merging and make the decisions as follows  
`if(lastTSAfterMerge -lastTSwindowed >20 )  `  
`long tempts = lastTSwindowed;`  
        `while(( tempts < (lastTSAfterMerge) )) {`  
   `ResultSet rs= mainstmt.executeQuery ("SELECT DISTINCT MAC FROM DATA WHERE PBID =" + String.valueOf(receivedID) + " AND TIME IN (" + String.valueOf(mintempts) + "," + String.valueOf(maxtempts) + ");"); `  
`timets+=20; `   
`}`

####Linear Estimator  
The linear relationship between the number of people and the number of MacAddress is obtained after training the Linear Squares method with some database. The linear estimate of the relationship we used is   
`NumPeople = (int) (0.7 + 0.9 * NumMac) `  
   
**Analysis of the Linear Relationship:** NumPeople = A + B * NumMac   
1. The **coefficient B** indicates the Number of MacAddress associated with single person. So, the coefficient B cannot be greater than 1 which indicates that there is more than one person with the MacAddress (i.e., static IPs)  
2. The **coefficient A** indicates the Number of people without the MacAddress.   

The database obtained after estimating the number of people for each PBID is used by the EL CLOUDO to represent the data graphically. 
 
##EL CLOUDO
###Scope of Subsystem
The final stage of the project utilized Google Fusion Tables to graphically represent the approximated data received from the Notorious S.V.R. (SVR). The Fusion Table is accessed using the Google Fusion Table API and the Fusion Tables API Client Library for Java. Communication with the Fusion Tables requires authentication with Google servers using the OAuth2 API Client Library for Java. This type of authentication is used when private information is to be transmitted using the API.  
 
The program created for uploading the data to a Fusion Table first accesses the SQLite database created by the SVR using a JDBC driver. The database table contains fields such as timestamp, number of unique MAC addresses at a given timestamp, and an approximation of the number of people present at the given timestamp (the owners of the electronic devices detected). The table also contains a field that indicates if the row has been pushed to the Fusion Table. The data that will ultimately be sent to the Fusion Table for analysis contains timestamps, number of users at a timestamp, and the ID number of the Promiscuous Box (PB) that collected the initial data set that was passed to the SVR.  
###Program Operation
The process of sending the data to the Fusion Table has several main steps. First, the program performs an SQL query on the table generated from the SVR which checks for any unadded entries to the Fusion Table. These entries are compiled into a result set which is then parsed for the relevant information such as the timestamp and number of users present at that timestamp. As the entries are parsed row by row, the parsed data is formatted into a new SQL statement that inserts each row of data into the Fusion Table. As each row is successfully sent to the Fusion Table, the original table entry is updated to reflect that it has been received by the Fusion Table. As the program transmits the SQL statement to the Fusion Table, it performs a check to ensure the command was successfully received with no error. The most commonly encountered error during transmission was exceeding the upload rate to the Fusion Table. The upload rate limit prevents a client from making excessive calls to the Fusion Table API in a small period of time. If this exception was thrown during execution, the program handles the error by sleeping for several seconds, reestablishing connection the Fusion Table, and attempts to resume transmission of the data. The program continues until verification is received that all data has been sent to the Fusion Table, then exits operation. 
Once the data has been pushed to the Fusion Table, several tools can be used to visualize and analyze the data. By including the timestamps of collected data, it is possible to view population trends of users in an area depending on the time of day. The data from the SVR can be processed in intervals of time, such as ten or thirty minute intervals, to highlight the trends in population at a specific time of day. The graphing and analytical capabilities of Fusion Tables allow for a simple and streamlined ability to analyze trends in large data sets such as those collected from the Promiscuous Boxes. 

##CONCLUSION

After testing our Collection over a 48 hour window, we obtained a 2 million entry database. This database was successfully transferred to the R2Data Android application and muled to the Notorious S.V.R. The Notorious S.V.R. then had to make two coefficient assumptions, which aren't available without further testing/learning. 

1. The **coefficient B** indicates the Number of MacAddress associated with single person. So, the coefficient B cannot be greater than 1 which indicates that there is more than one person with the MacAddress (i.e., static IPs)  
2. The **coefficient A** indicates the Number of people without the MacAddress.   

While these two coefficients are only educated guesses at this point, we were able to to see a rough estimate of people over time. 

The database obtained after estimating the number of people for each PBID is used by the EL CLOUDO to represent the data graphically. 
