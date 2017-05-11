# Project 1

## Team Members
* Blade Roybal - Promiscuous Box
* Randy Neal - R2Data
* Ian DeGroot - The Notorious S.V.R
* Nagaraj Janakiraman - el Cloudo

[Report Wiki page](https://github.com/CourseReps/ECEN489-Spring2015/wiki/Project-1-Team-4---Report)

## Critical Needs

**Promiscuous Box**
* Turn card into promiscuous mode and detect/read MAC addresses - complete
* Store MAC addresses in database - complete
* Push data to android phone - complete

**R2Data**
* Get data from PB using Bluetooth - Transferring .db file via bluetooth from PB to R2Data. (Successful 23Feb15)
* Give data to The Notorious S.V.R using WIFI. (Successful 25Feb15)
* Merge the bluetooth and wifi Android apps into a unified app that will interface with PB java program and Notorious SVR java program. (Successful 27Feb15) 

**The Notorious S.V.R**
* Get data from R2Data using WIFI - Have successfully transferred .db file over socket from java client to Not. SVR, will port over to Android. 

**el Cloudo**
* Get data from The Notorious S.V.R - 
* Display processed data using Fusion Tables

## Deliverables

**Promiscuous Box**

M - Read MAC addresses & Load data into database- Complete

W - Improve parsing to filter results to remove duplicate MAC addresses, to within one second.

F - Interface with android

**R2Data**

M - Collect database file from PB - (Complete 23Feb15)

W - Transfer database from the local file to Notorious S.V.R. Began unifying the android apps to receive and send the .db file from PB to R2 to SVR. (Complete 25Feb15)


F - Have a working app that unifies the PB and SVR android apps that will send the .db file from pb to R2 and then to SVR. (Complete 27Feb15)

**The Notorious S.V.R**

M - Create simple server - Complete

That server listens for R2Data. 

W - Interface with droid, merge database files

**RESOLVE: Are we pushing a serialized object or DB file?** Resolved: the .db file will be sent over the bluetooth connection and then that same .db file will be sent over file input/output stream to the Not. SVR.

F - Interface with el Cloudo

**el Cloudo**

M - Get access to fusion tables using OAuth2 and push data to Cloud - Complete

W - Data visualization in fusion tables

Update: Perform Windowing and linear estimate of users in area based on number of MAC addresses in an area
with respect to the number of actual people there.

F - Data visualization in fusion tables