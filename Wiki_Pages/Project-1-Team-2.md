## Project Goals
### Promiscuous Box
The program in this directory will use the PCAP library to access the wireless card directly and pull MAC data from Ethernet frames flying through the air. It outputs the information into a SQLite database and creates a new database every so many entries (default: every 100,000 frames). The intention is that a second Java program will come in behind it every so often and parse the files/populate an SQLite database.

### R2DATA MULE
Create an interface between the PB and an Android App using Bluetooth, where incoming data with a root table and a primary table (w/ time stamps and MAC addresses) will be gathered and stored in a Sqlite database and then send it to the Linux server.

### Notorious S.V.R
Will continuously retrieve Sqlite database files from the R2Data application through a socket over a WiFi connection. Everything collected from the app will be stored in a single Sqlite database local to the server's computer. Lastly, the server will ensure that there is no duplicate data being stored in the .db file.

### El Cloudo
The data collected in the server's database file will be represented using Fusion Tables.

## Deliverables
#### Promiscuous Box: George Sullivan (I-Love-Github)
***
| Date          | Deliverable   | Met?| Working on|
|:-------------:|:-------------:|:---:|:---:|
| February 23   | Set wifi card to monitor mode and sniff, sort, and store MACs in a DB  |:+1:|  |
| February 25   | PB needs to sort out user MAC addresses and remove access point MACs while sorting DB |:+1:| |
| February 27   | Write code that will sort out uninteresting MAC addresses |     | Still in-work |

#### R2DATA Mule: Anudeep Tungala (nranudeep1990)
***
| Date          | Deliverable   | Met?| Working on|
|:-------------:|:-------------:|:---:|:---:|
| February 23   | Define an interface to push data from PB to R2Data|:+1:| |
| February 25   | Have R2D receive database files over bluetooth|:+1:| |
| February 25   | Implement the interface PB as a server and Android as client |:-1:| Able to make connection, but facing problems in ServerHandler implementation for Obex. Server not responding for client request to get data|
| February 27   | Ready a prototype that works at least with individual components |:+1:| Need to check with server code(Joshua)|

#### The Notorious S.V.R.: Joshua Cano (jacano23)
***
| Date          | Deliverable   | Met?| Working on|
|:--------------:|:-------------:|:---:|:---:|
| February 23  | Collect data over wifi and store data in a local SQLite database  |:+1:| |
| February 25  | Delete duplicates of database entries and agree with other teams on the interface |:+1: | Interface between R2 and Server is agreed upon. Working on removing duplicates |
| February 27  | Have individual component ready and able to connect with R2data and transfer information| :+1:   | |

#### El Cloudo: Rong Liu (liur180855)
***
| Date          | Deliverable   | Met?| Working on|
|:-------------:|:-------------:|:---:|:---:|
| February 23   | Resolve Oath issue with the fusion tables  |:-1:| |
| February 25   | Complete Deliverable from Feb 23  |:+1:| Currently looking at putting db file into fusion table|
| February 27   | Parse a db file and upload it to fusion table|:+1:|I can put db file on there.|