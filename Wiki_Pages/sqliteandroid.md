##SQLite in Android
SQLite is embedded into every Android device. Using an SQLite database in Android does not require a setup procedure or administration of the database.

You only have to define the SQL statements for creating and updating the database. Afterwards the database is automatically managed for you by the Android platform.

Access to an SQLite database involves accessing the file system. This can be slow. Therefore it is recommended to perform database operations asynchronously.

If your application creates a database, this database is by default saved in the directory
>DATA/data/APP_NAME/databases/FILENAME

The parts of the above directory are constructed based on the following rules. DATA is the path which the Environment.getDataDirectory() method returns. APP_NAME is your application name. FILENAME is the name you specify in your application code for the database.

##SQLite architecture
### Packages

>android.database

 contains all necessary classes for working with databases.

>android.database.sqlite

 contains the SQLite specific classe.

### Creating and updating database with SQLiteOpenHelper
what is SQLiteOpenHelper? 

A helper class to manage database creation and version management.

To create and upgrade a database in your Android application you create a subclass of the SQLiteOpenHelper class. In the constructor of your subclass you call the super() method of SQLiteOpenHelper, specifying the database name and the current database version.

In this class you need to override the following methods to create and update your database.

* onCreate() - is called by the framework, if the database is accessed but not yet created.

* onUpgrade() - called, if the database version is increased in your application code. This method allows you to update an existing database schema or to drop the existing database and recreate it via the onCreate() method.

Both methods receive an SQLiteDatabase object as parameter which is the Java representation of the database.

The SQLiteOpenHelper class provides the getReadableDatabase() and getWriteableDatabase() methods to get access to an SQLiteDatabase object; either in read or write mode.

The database tables should use the identifier _id for the primary key of the table. Several Android functions rely on this standard.

_**Useful Tips:**_

It is good practice to create a separate class per table. This class defines static onCreate() and onUpgrade() methods. These methods are called in the corresponding methods of SQLiteOpenHelper. This way your implementation of SQLiteOpenHelper stays readable, even if you have several tables.

### SQLiteDatabase
SQLiteDatabase is the base class for working with a SQLite database in Android and provides methods to open, query, update and close the database.

More specifically SQLiteDatabase provides the insert(), update() and delete() methods.

In addition it provides the execSQL() method, which allows to execute an SQL statement directly.

The object ContentValues allows to define key/values. The key represents the table column identifier and the value represents the content for the table record in this column. ContentValues can be used for inserts and updates of database entries.

Queries can be created via the rawQuery() and query() methods or via the SQLiteQueryBuilder class .

rawQuery() directly accepts an SQL select statement as input.

query() provides a structured interface for specifying the SQL query.

SQLiteQueryBuilder is a convenience class that helps to build SQL queries.















## Reference 
http://developer.android.com/

http://www.vogella.com/tutorials/AndroidSQLite/article.html#overview_sqliteandroid