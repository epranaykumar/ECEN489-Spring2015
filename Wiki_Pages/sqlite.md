#SQLite Introduction
SQLite is a software library that implements a self-contained, serverless, zero-configuration, transactional SQL database engine. SQLite is the most widely deployed SQL database engine in the world. The source code for SQLite is in the public domain.

##Advantages: 
* No server needed, easy to transfer
* Usually used to mobile device
![](http://4.bp.blogspot.com/-3iS-Oc8JKJ4/T3HzGEqqFdI/AAAAAAAAAEY/bhHFKbQ2QDw/s1600/android_sqlite.png)

##Basic command([SQLite Syntax](http://www.sqlite.org/lang.html))
* create a database 
>sqlite3 database.db

* create tables
>create table student(ID, name varchar(20), Major varchar(10));

* edit entries
>insert into student values(101, 'Stephanie', 'EE');

* Display the table
>select * from student;    

* Delete entries
>DELETE FROM student WHERE ID=101;

#Data Types supported 
SQLite uses dynamic typing. Content can be stored as INTEGER, REAL, TEXT,[BLOB](http://en.wikipedia.org/wiki/Binary_large_object), or as NULL.

SQLite allows you to store BLOB data in any column, even columns that are declared to hold some other type. BLOBs can even be used as PRIMARY KEYs.

#Suggested Uses For SQLite:
* Application File Format. Rather than using fopen() to write XML, JSON, CSV, or some proprietary format into disk files used by your application, use an SQLite database. You'll avoid having to write and troubleshoot a parser, your data will be more easily accessible and cross-platform, and your updates will be transactional.

* Database For Gadgets. SQLite is popular choice for the database engine in cellphones, PDAs, MP3 players, set-top boxes, and other electronic gadgets. SQLite has a small code footprint, makes efficient use of memory, disk space, and disk bandwidth, is highly reliable, and requires no maintenance from a Database Administrator.

* Website Database. Because it requires no configuration and stores information in ordinary disk files, SQLite is a popular choice as the database to back small to medium-sized websites.

* Stand-in For An Enterprise RDBMS. SQLite is often used as a surrogate for an enterprise RDBMS for demonstration purposes or for testing. SQLite is fast and requires no setup, which takes a lot of the hassle out of testing and which makes demos perky and easy to launch.

#Interfaces
* [C/C++ Interface Spec](https://www.sqlite.org/c3ref/intro.html)
* [The TCL Interface Spec](https://www.sqlite.org/tclsqlite.html)

Useful Links:
* [sqlite.org](https://www.sqlite.org/)
* http://zetcode.com/db/sqlite/datamanipulation/
* http://www.tutorialspoint.com/sqlite/sqlite_delete_query.htm
