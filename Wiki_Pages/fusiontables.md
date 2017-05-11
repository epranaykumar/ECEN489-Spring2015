# Google Fusion Tables
![](http://icons.iconarchive.com/icons/alecive/flatwoken/512/Apps-Google-Drive-Fusion-Tables-icon.png)
## Introduction
Google Fusion Tables are part of an experimental tool set created by Google to gather and visualize data. They are powered by a online interface that provides a rich feature set to viewing and analyzing data in a whole new way. Fusion Tables are also easily shared with others for viewing or collaboration. Fusion Tables can be used to create many simple graphical representations of collected data such as pie charts, bar charts and line graphs. Aside from conventional representations of data, they can also be used to create interactive visualizations such as dynamically plotting data on a map using geographical coordinates.  

## Interacting with Fusion Tables Using the Web Interface
Perhaps the simplest way to interact with Google Fusion Tables is using the online web interface. From this interface it's possible to perform a range of operations such as creating, editing, and viewing entries in the table. The web interface also supports uploading spreadsheet files, such as XLS or CSV data, to quickly import into a Fusion Table. The web interface is useful if you want to link the Fusion Tables application to your Google Drive cloud storage account from which you can easily manage your Fusion Tables and import spreadsheets from applications like Microsoft Excel.

The web interface is also useful when you want to put the Fusion Table to work to visualize your data. From the web interface you can quickly get an overview of the data in your table similar to how a spreadsheet would look. If any online assets are included in the table, such as links to a picture, those will be automatically rendered inline with the data. Provided the appropriate types of data are included in the table, the web interface also allows the user to visualize the data in a number of ways like creating a map using latitude and longitude coordinates or generating a heat map that represents the density of data from the table.

## Interacting with Fusion Tables Programmatically 
Google also allows users to interact with and manipulate Fusion Tables using the Fusion Tables API. The user can use the API to perform all the functions available under the web interface by using HTTP requests. Requests to the Fusion Tables API for public data must be accompanied by an identifier, which can be an API key or an access token. An API key identifies your project and provides public access to a table when the key is used in a request. Once the key is obtained, your application can append the query parameter `key=yourAPIKey` to all request URLs.

Requests to a Fusion Table are performed using RESTful syntax or SQL statements. The REST or SQL keywords used for a specific command are appended to a URI (Unique Resource Identifier) specified in the Fusion Tables API. The HTTP request must also contain the `tableId` which is the unique identifier for a specific table in a Fusion Table. This identifier can be obtained in several ways including the web application. It is simply an encrypted string value that is used to reference a specific table. 

Table structure, column, style, and template data is handled using RESTful HTTP requests while row data is handled using SQL queries. REST HTTP requests return data as JSON data structures while SQL HTTP requests can return data in either CSV or JSON formats. The URI format used with REST HTTP verbs may look something like:  
`https://www.googleapis.com/fusiontables/v2/tables/tableId/columns`  
while the format used with SQL statements may look something like:  
`https://www.googleapis.com/fusiontables/v2/query?sql=query`  
More info on REST can be found on these web pages (from the getting started page):  
[Building Web Services the REST Way](http://www.xfront.com/REST-Web-Services.html)  
[HTTP 1.1 method definitions](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)  


## Creating a Google Fusion Tables Project
### Creating a Fusion Table in Google Drive
* First, the you must register with with Google by [creating an account]      (https://www.google.com/accounts/NewAccount) if you have not done so already. 
* Head to [Google Drive](https://www.google.com/drive/) and login with your Google account
* Under the _New_ button on the left pane, select _more_ then select _connect more apps_
* In the search box type in _Fusion Tables_ then click _Connect_
* Now under the _New_ button in the _more_ category there should be an option to create a new Google Fusion Table

### Creating a Project Using the Developers Console
* First, the you must register with with Google by [creating an account]      (https://www.google.com/accounts/NewAccount) if you have not done so already. 
* Next, go to the [Google Developers Console](https://console.developers.google.com/)
* Click the _Create Project_ button and choose your project name and project ID

### Generating Client IDs and Public API keys
* Under the _APIs & auth_ section on the left, choose _APIs_ and enable the _Fusion Tables API_ 
* You will also need your SHA1 key if you are creating a _Client ID_ for an Android application. In order to find the SHA1 key for Android Studio, first find the directory where the `debug.keystore` is located. It will most likely be in a directory similar to `C:\Users\User_Name\.android`. Next, open a terminal and navigate to the directory where the `keytool.exe` program is located. It will most likely be in a directory similar to `C:\Program Files\Java\jre7\bin`. Finally, run the command  
 `keytool -list -v -keystore C:\Users\User_Name\.android\debug.keystore`. If the terminal asks for a password the default password is _android_. The terminal should then display all certificate fingerprints including your SHA1 key.
* You should then navigate to the _Consent screen_ under _APIs & auth_ and fill in the appropriate fields for your project. You **must** at the very least include your _email address_ and _product name_ to create a working client ID. The consent screen is what will be shown to users whenever you request access to their private data using your client ID. Once the information is provided, you can then create a new _Client ID_ with OAuth 2.0
* To create a Client ID for a **Java application** (i.e. for an application created in IntelliJ), click _Create new Client ID_ under the _Credentials_ tab. Then choose the _Installed application_ button then choose the _Other_ button. Click _Create Client ID_ to generate a new ID for use. Once the ID is generated, click the _Download JSON_ button to obtain the JSON object used in the Java application for OAuth2 authorization. 
* To create a Client ID for an **Android application**, click _Create new Client ID_ under the _Credentials_ tab. Then choose the _Installed application_ button then choose the _Android_ button. Enter in your SHA1 signing key, your application package name, and leave the _Deep Linking_ option disable. Click _Create Client ID_ to generate the ID for an installed Android application. More info on deep linking can be found [here](https://developers.google.com/+/mobile/android/share/deep-link)
* Public API keys are used when no private information is to be shared. API keys do not require user action or consent and do not grant access to private information nor are they used for user authentication.
* Additional info on authorization requests can be found [here](https://developers.google.com/fusiontables/docs/v1/using#auth).

###Setting Up the Java Project to Utilize the Fusion Tables API
* Add the appropriate JAR files to your project class path. The majority of the JAR files can be obtained from the [API Client Library for Java.](https://developers.google.com/resources/api-libraries/download/fusiontables/v2/java) I added the entire _lib_ folder along with `google-api-services-fusiontables-v2-rev1-1.19.1` JAR file.  
* You will also need to add the `servlet-api` JAR to the class path. This is used to launch a web page which allows the client to authenticate and allow changes to be made to the client Fusion Table. I obtained this JAR from the [Apache Tomcat](http://tomcat.apache.org/download-70.cgi) _lib_ folder. It can also be obtained from other sources but I found this to be the easiest. You may also download it from my [Google Drive.](https://drive.google.com/open?id=0B8nAMK2N4ng_bS1ROXNfekpxaFk&authuser=0)
* You will need to then obtain the Client ID JSON object (detailed in the previous section) for your project. Add this file to the application project in the main `src` directory. 
* The application should now be configured to run and any additional changes needed by the user can be made. I have uploaded a commented _sample project_ to GitHub that simply needs to be configured using the method described above. Just be sure to use your respective Client ID JSON and the Fusion Table(s) located in your Google Drive. 

![Web Server Applications](https://developers.google.com/accounts/images/webflow.png)




## Useful Links
###Documentation
[Google Developers Console](https://console.developers.google.com/)  
[Fusion Tables Tutorials Page](https://support.google.com/fusiontables/answer/184641?hl=en)  
[Getting Started](https://developers.google.com/fusiontables/docs/v2/getting_starte)  
[Using the API](https://developers.google.com/fusiontables/docs/v2/using)  
[SQL Queries](https://developers.google.com/fusiontables/docs/v2/sql-reference)  
[Using OAuth 2.0 to Access Google APIs](https://developers.google.com/accounts/docs/OAuth2)  
[Using OAuth 2.0 for Authorization to Fusion Tables in Web Applications](https://developers.google.com/fusiontables/docs/articles/oauthfusiontables)   
[Building Web Services the REST Way](http://www.xfront.com/REST-Web-Services.html)  
[HTTP 1.1 method definitions](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)   
###Downloads
[Fusion Tables API Client Library for Java](https://developers.google.com/api-client-library/java/apis/fusiontables/v2)  
[OAuth2 Client Library for Java](https://developers.google.com/api-client-library/java/apis/oauth2/v2)  
[Google API Sample Java Code Bundle](https://samples.google-api-java-client.googlecode.com/archive/b9f8cdb9ef45d027079e946d51a10452c6f5910b.zip)  
[Apache Tomcat 7](http://tomcat.apache.org/download-70.cgi)  
[Servlet API](https://drive.google.com/open?id=0B8nAMK2N4ng_bS1ROXNfekpxaFk&authuser=0)



