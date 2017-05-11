# Project 1 Overview
The goal of this project is to sniff use a WiFi card in monitor mode to sniff wireless frames traveling over the air.  The number of MAC addresses seen by the card are recorded and logged over time in an SQL database.  Due to the secure nature of the TAMU network, the frames are encrypted and their content cannot be read by the wireless sniffer -- only the local MAC addresses.

This project is accomplished through the use of four layers: a Linux WiFi sniffing box (The Promiscuous Box), an Android data mule (R2Data), a server where the information is stored and analyzed (The Notorious S.V.R.), and a Google fusion table where the information will be displayed (El Cloudo).

In-depth description of each layer can be found below.  The project goals wiki page that was used for the development of the project can be found here:  [Project 1 Team 2 Wiki Page](https://github.com/CourseReps/ECEN489-Spring2015/wiki/Project-1-Team-2)

## The Promiscuous Box
The PB performs the wireless sniffing operation and stores the information in an SQL database.  This subsystem is made up of three components:

### WiFi Sniffer
This component performs the wireless sniffing operation using the PCAP library to collect the WiFi frames as they are picked up by the wireless card.  The MAC addresses are stripped from the frame header and dumped into a CSV file.  [The code for this program can be found here.](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Project1/Team2/PromiscuousBox)  The CSV file contains timestamp information, source, and destination MAC addresses.

### Database Parser
This component reads in the CSV file and puts relevant information into an SQL database.  The MAC sniffing is broken down to a resolution of one second.  For every second that passes (determined by the sniffed timestamp, rounded down), all duplicate MAC addresses are removed and inserted into the database file.  The DB file contains timestamp and MAC fields.  Source and destination information is dropped as it is not important information.

### Bluetooth Communications
This component takes the DB file, makes a copy of it, and sends it off to the R2Data layer.  The details of this transaction are handled in that section.

## R2Data

Basically this module has three submodules

Bluetooth Interface on PB. [(Code)](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/nranudeep1990/Project1/SimplePBServer)

Android Client. [(Code)](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/nranudeep1990/Project1/R2DATA2)

Wi-Fi Interface. [(Code)](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/nranudeep1990/Project1/R2DATA2)

### Bluetooth Interface on PB:
This submodule facilitates the communication between the PB Server and Android Client using Bluetooth. PB Server will initiate a simple SPP service and broadcasts its service so that the clients 
can detect it through service discovery and connect to it. When it gets connected to a client, this interface
 module gets the Client name, then it sends back a handshake signal having server name, then the client sends
 back the lastPbID and lastSVRId, after these handshakes the server creates a copy of the database in the 
local folder with name “clientData”+localprombox.db. It then eliminates the unwanted rows based on the
 lastSVRId and lastPbId handshakes. So now the temporary database created particularly for the connected
 client is transferred on OutStream on TCP socket. Once the transfer is completed, Server closes the socket.
 Server can simultaneously connect to multiple clients and perform the tasks assigned to it.

### Android Client:
Android client bridges the two different interfaces and presently it is manually operated to perform its tasks. For the Bluetooth part the mac addresses were hardcoded into the client as a drop down list and we need to select the Mac address to connect and click on load data to initiate the Bluetooth connection with server. It downloads the .db file from the server and stores it in the Downloads with PB”name”.db . Before it starts download client checks for an existing file from the server it got connected and deletes if any. This will be done without any hassles as client is programmed to perform the different tasks in Async mode so that one tasks doesn’t interfere with the other. 

### Wi-Fi Interface:
For the Wi-Fi interface, when user enters IP and port to connect, it checks for the existing files and in a loop fashion it creates a connection to the server and transmits the first file and closes the connection and then repeats the process for the remaining existing files. After it creates a connection, the Wi-Fi interface just forwards the existing file in form of byte stream over the socket and shutdowns the outstream. Once the main server receives this file it send backs a confirmation of last successfully received Id and it will be updated as lastSVRId on android client corresponding to the PB Id.


## The Notorious S.V.R
The server receives the .db files from R2Data through a socket over a Wi-Fi connection. Each database being sent is uniquely named by a timestamp when R2Data pulled the data from the PB. The server then parses the databases received into a single organized SQLite database. 

### Process
The server will be constantly running waiting for an android device client to connect to the server socket. If it connects, a database file will be transferred by using file input and output streams. The two tables in the read database file are then merged into another database that conjoins the two tables.

To merge the tables, we use several SQLite commands that read and writes information into the main database. However, before writing the new data, we check whether there are duplicate data based on the timestamp and the mac address. Finally, we made a new table that will be used for analytics. The main data analysis implemented by the server before sending the database to the Fusion Table was Windowing and Linear Regression.

#### Windowing
Finds the unique MAC addresses within some period of time.

#### Linear Regression
Estimates the number of people per window based on the training set that is inputted along with the windowed data.

### Final Database Organization
The final merged database after windowing and linear regression will have the time_stamp, num_macs, num_people, the PbID and an added column that states whether the row of data was transferred to the fusion table or not.


## El Cloudo

The primary function of El Cloudo is to authenticate using oauth2.0 and connect to fusion table and take the database coming in from The Notorious S.V.R and update the database and then upload the column of TIME, NUM_PEOPLE, and PBID.

###OAuth2.0

OAuth 2.0 supports application that are installed on a device such as computer, cell phone, or tablet. The main purpose of using OAuth 2.0 is to authenticate without giving password and username away. The first step was to redirects a browser to Google URL and indicate which Google API is in use. Second, Google will handle user authentication. Third, authorization code would be exchanged for an access token and a refresh token. This is done by presenting client ID and client secret. Finally, application would store the refresh token and ultimately use it for future use.

###Connect to db

Use JDBC library to read from and write to the database. First part is connecting to the database. Second, extract one row of TIME, NUM_PEOPLE, and PBID and perform linear regression to NUM_PEOPLE using NUM_MACS and then upload these 3 data. Finally, the database would be updated.