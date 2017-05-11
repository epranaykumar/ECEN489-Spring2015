# The Java Database Connectivity (JDBC)
JDBC is a Java database connectivity technology from Oracle Corporation. It was first released on February 19, 1997 and since then has been a part of the Java Standard Edition. This technology is an application program interface (API) for Java that defines how a client may access a database. It provides methods for querying and updating data in a database. The JDBC API provides a call-level API for SQL-based database access.
JDBC allows you to use Java to utilize "Write Once, Run Anywhere" capabilities for applications that require access to enterprise data. With a JDBC technology-enabled driver, you can connect all corporate data even in a heterogeneous environment.

## How JDBC Works
Simply, JDBC makes it possible to do the following things within a Java application:
* Establish a connection with a data source
* Send queries and update statements to the data source
* Process the results

The Following figure shows the components of the JDBC model

![](http://i.imgur.com/4mCJK62.jpg)

The Java application calls JDBC classes and interfaces to submit SQL statements and retrieve results.

The JDBC API is implemented through the JDBC driver. The JDBC Driver is a set of classes that implement the JDBC interfaces to process JDBC calls and return result sets to a Java application. The database (or data store) stores the data retrieved by the application using the JDBC Driver.

## The main objects of the JDBC API include:

__**DataSource object**__ is used to establish connections. Although the Driver Manager can also be used to establish a connection, connecting through a DataSource object is the preferred method.

**Used to establish a connection to the Driver**

     Class.forName("org.sqlite.JDBC");

_**Connection object**_ controls the connection to the database. An application can alter the behavior of a connection by invoking the methods associated with this object. An application uses the connection object to create statements.

**Used to establish a connection to the Database**

     DriverManager.getConnection("jdbc:sqlite:client.db");

_**Statement, PreparedStatement, and CallableStatement objects**_ are used for executing SQL statements. A PreparedStatement object is used when an application plans to reuse a statement multiple times. The application prepares the SQL it plans to use. Once prepared, the application can specify values for parameters in the prepared SQL statement. The statement can be executed multiple times with different parameter values specified for each execution. A CallableStatement is used to call stored procedures that return values. The CallableStatement has methods for retrieving the return values of the stored procedure.

**Basic statement to Create a table**

      String sMakeTable = "CREATE TABLE dummy (id numeric, response text)";
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sMakeTable);

**Basic statement to insert to table**

     String sMakeInsert = "CREATE TABLE dummy (id numeric, response text)";
     Statement stmt = conn.createStatement();
     stmt.executeUpdate(sMakeInsert);

**Basic Prepare Statement to update table**

     String updateTableSQL = "UPDATE DBUSER SET USERNAME = ?" + " WHERE USER_ID = ?";
     preparedStatement = conn.prepareStatement(updateTableSQL);
     preparedStatement.executeUpdate(updateTableSQL);

_**ResultSet object**_ contains the results of a query. A ResultSet is returned to an application when a SQL query is executed by a statement object. The ResultSet object provides methods for iterating through the results of the query.

**This is the Result Set code to select a result**

     String sMakeSelect = "SELECT response from dummy";
     ResultSet rs = stmt.executeQuery(sMakeSelect);

### Helpful Links and Resources
* https://www.progress.com/products/datadirect-connect/jdbc-drivers/jdbc-developer-center/jdbc-faqs/how-does-jdbc-work 
* http://www.techopedia.com/definition/1212/java-database-connectivity-jdbc 
* http://www.webopedia.com/TERM/J/JDBC.html
* http://www.oracle.com/technetwork/java/overview-141217.html
* http://en.wikipedia.org/wiki/Java_Database_Connectivity
