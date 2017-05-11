# Project Scope
Promiscuous Box: 
* Turn network card on promiscuous mode
* Create database for communicating with android database

R2Data:
* Define interface between Promiscuous box and device
* Store data from box into local android database

Notorious S.V.R.:
* Transfers data from android device (R2Data) to server SQL database
* Vote on whether local server (Nuk box) or static IP server should be used
* Define interface between device and server

El Cloud:
* Solve OAuth authentication issues



# Project Deliverables
## 2/23/15 
**DONE** Promiscuous Box (Mandel): Process and attempt to parse dumped data from wireless card. 

R2Data (Benito): Collaborate and define interface between PB and R2Data. Investigate database design and format. 

Notorious S.V.R.(Pranay): Create prototype java server that uses SQL database to interface with R2Data.

**DONE** El Cloud (Trevor): Investigate OAuth authentication and have an authentication procedure working.

### Progress Update
Promiscuous Box (Mandel): Progress made monitoring client mac addresses on network. Wrote script and parser that utilizes hashmaps to find and track users on the network.

R2Data (Benito): Still working.

Notorious S.V.R.(Pranay): Defined database layout.

El Cloud (Trevor): Created working java code that can manipulate a Fusion Table.

##2/25/15
Promiscuous Box (Mandel): 

Further filtering of data using MAC address companies.  

Create DB from filtered data.

R2Data (Benito): 

Progress 2/25/14: Have met with the rest of R2Data group and discussed interfaces between PB-R2Data and R2Data-TNSVR. For interface between PB-R2Data we will have a database file sent through bluetoth and then that database file will be sent through a WIFI connection to the TNSVR. The database will consist of two tables. Table one has a time stamp column and a mac address column. Table two has the PBID number. Currently working on the android app that will both receive and send the db files.(Cont.)

Notorious S.V.R. (Pranay): Define serializable object on wiki. Ensure other groups are using same object. 

Progress 2/25/14:    
Discussed with other groups and finalized the interface between the R2data and SVR. The R2Data sends a .db file with two tables (these tables were defined by the PB box group) and the server receives the .db file and parses it and appends it to the existing sqLite database. The server also makes sure that there are no duplicates (same timestamp and Macaddress) of the entries in the sqLite database.  

Working on the server that receives .db file from the android device.

El Cloud (Trevor): Update Google Fusion Table wiki page. Demonstrate working code to class.

###Progress Update
Promiscuous Box (Mandel): Scripting mostly complete. Working on refining filtering algorithm of incoming packets. 

R2Data (Benito):  Successfully received data from PBox. Working on finalizing interface between R2Data and NSVR.

Notorious S.V.R.(Pranay): 

El Cloud (Trevor): Finished updating wiki page and uploaded sample fusion table application to GitHub.


##2/27/15
Promiscuous Box (Mandel): Have timing interval updated to 1 sec. Have working prototype demonstrating parsing and filtering abilities of detected mac addresses.

R2Data (Benito):  

Notorious S.V.R.(Pranay): 

El Cloud (Trevor): 
