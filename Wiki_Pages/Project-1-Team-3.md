#Project 1
##Team Members
* Hong Pan, [hongpan0507](https://github.com/hongpan0507) | PromiscuousBox 
* Yajie Zeng, [yjzeng8833](https://github.com/yjzeng8833) | R2Data
* Kevin Christopher Wilkens, [kevinwilkens22](https://github.com/kevinwilkens22) | The Notorious S.E.R.V.E.R.
* Evelyn Susanah Cifuentes, [cifuentesevelyn](https://github.com/cifuentesevelyn) | elCloudo

##Project Goals

###Sensor Box (PB)

**Module Lead: Hong**

Read MAC Addresses, filter addresses not belonging to personal devices, add into SQLite database along with an additional column with date/time stamp.  Database sent to R2Data will include 2 tables:  one table with a column for MAC addresses and another column for date and time stamp associated with each MAC address, and another table with some identification for the PB that the data was recorded on.

#### Android Mule (R2Data)

**Module Lead: Yajie**

The interface from PB to R2Data will be implemented using Bluetooth. A Java server program will be written to take the local SQL database file and based on the time stamp in the PB database file and the time stamp of the last transmission to the server the R2Data will query the PB, create a small temporary database locally, and send that database to the R2Data. This database will have a root table (with the PB id) and the primary table (with the mac address and time stamp). The database will then be sent to the Notorious SVR for comparisons.

####Linux Server (The Notorious S.V.R.)

**Module Lead: Kevin**

This program will read SQLite database files from R2Data, write the data from these files into a single database local to the server computer, and will run a check to ensure that no duplicate data is written into the server database file.

#### Fusion Tables (El Cloudo)

**Module Lead: Evelyn**

Write data to Fusion tables using Java.

##Deliverables

####Sensor Box (PB)

*Monday 2/23/2014 Deliverables:* Present MAC address data read by the network card in monitor mode.  Also filter some MAC addresses.

**Status:** Network card is reading addresses, and parsing out duplicate mac addresses.  

*Wednesday:* Knock readings down to a 1 second interval.  Store and organize MAC Addresses in SQLite database.

*Friday:*
insert mac to database;
filtering the the mac based on the type of the device

*Monday 3/2/2015:*
PB reads MAC info, filters out devices that don't represent a person, and only adds one entry to the database per second.  Also sends the database to the android via bluetooth.

####Android Mule (R2DATA)

*Monday 2/23/2014 Deliverables:* Get interface between PB and R2Data. 

**Status:** We have an Android prototype that will send a .db file created by the Android device to a Java server.  

*Wendesday:*  Need to coordinate with other teams to get a common transfer method from PB to R2Data.
**Status: Completed** The transfer method via bluetooth has been agreed upon by all R2DATA team leads.  

*Friday:* Finish transferring file from PB to SVR.

*Monday 3/2/2014 Deliverables:* Receive a database file successfully via bluetooth from the nuc and place it in the documents folder.  Also send the .db file to the Server.


####Linux Server (Notorious SVR)

*Monday 2/23/2014 Deliverables:* 

**2/23/2014 Status:** We have a server program in Java that will read a .db file from R2Data and name it with a timestamp to distinguish it from other .db files read from R2Data.  

*Wednesday:* Synchronize with other teams to get a common interface between R2Data and Server.  Implement one of the interfaces and organize data into a single, organized database.

**Status:**  It has been established that we will be reading database from the Android device.  The databases will be named with a timestamp based on when R2Data pulled the data from the PB, and the SVR will parse the databases received from R2Data into a single organized database. There will be two tables in each .db file received from R2Data, one table will include only the PBID of the box that the data is pulled from, and the other table will include the MAC address and date/time stamp info. The final SQLite database on the SVR will have 3 columns, one column with date/time stamp, one column with the MAC address, and another column with a PBID that tells which promiscuous box the date, time, and MAC address came from.

2/25/2014 status:  Completed Wednesday deliverable commitment.

*Friday:* Have a method of filtering duplicate entries from the main database on the server.  Send R2Data timestamp of last entry in the .db file we receive, and pbid each time a .db file is received.  Send the timestamp and pbid as two separate lines.

*Monday 3/2/2014 Deliverables:* Receive a database file successfully via wifi from the android, and place it in the main database file on the server without duplicating information.  Also need to send data successfully to a fusion table.

####Fusion Tables (El Cloud)

*Monday 2/23/2014 Deliverables:* Oauth (work on as a group)
Status: in Progress

*Wednesday:*  Get Oauth working.

**Status:** Oauth is setup and working. Next step is getting data into fusion tables.

*Friday:* Push data from SVR to El Cloudo.

*Monday 3/2/2014 Deliverables:* Receive data from the main database on the server and display the data.