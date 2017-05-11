# Project 3: ScoutOut

## Physical Devices and Virtualization

Team Members:
* Kevin Wilkens  

### Function  

This division is responsible for initial setup of the physical and virtual server containing the back-end for our product.  This involves setting up a reliable security firewall that can be easily implemented and will allow users access to app information, and will allow any other division who is responsible for software implemented on the server to have access to their respective software through a secure channel.  

After initial setup, the responsibilities of this division shifts to maintaining the security and integrity of the server, while assisting the any other division needing assistance with work they are responsible for on the physical or virtual servers.

## Networking and Server-Client Architecture

Team Members:
* Joshua Cano (Android App Structure and Integration)
* Rong Liu (SSL Certificates)
* Pranay Eedara (Android Opencv Integration and server-client using Java Sockets)
* Anudeep Tungala (Network Administration and Service Hosting)

### Android Client CheckIn Using Digit Recognition  
  
For CheckIn of the android client using ImageProcessing, digit recognition is preferred over other image processing techniques as this can distinguish several CheckIn Locations. 
  
Digit Recognition on android using OpenCV libraries involves training and testing of the selected classifier. In this project, the k-Nearest Neighbor(KNN) classifier is used. In training phase, the training data (i.e., Samples and their responses) for training the classifier is created and stored in a bitmap file and in testing phase, digits are extracted from the raw image and fed to the trained-KNN classifier in the appropriate format to get the recognised number. The implementation involves following steps  
    
#### Getting Binary Image of digits from Raw Image  
1. Image Preprocessing  
    (a) Get Binary Image of input image using either adaptive threshold or Simple Threshold or Inrange functions  
        For example:   
         `Imgproc.adaptiveThreshold(inputimage, thresholded_image, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, blockSize, meanOffset);`  
  
    (b) Remove noise and thicken the width of digits by performing the morphological closing followed by opening  
        For example: To Perform Morphology close

        ` //get kernel`
        `org.opencv.core.Size kernelsize =new org.opencv.core.Size(7,7); // 7,7 is optimum (if less thin digits) (if more than 7, more dots)`
        `Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernelsize);// these values doesn't depend on color`

        `// apply Morphology close`
        `Mor_Close_im = new Mat();`
        `Imgproc.morphologyEx(adpthr_image, Mor_Close_im, Imgproc.MORPH_CLOSE, kernel);`
2. Find Contours of digits and filter the contours so that they point only to digits. Centroid of contours is used for filtering.
        For example: To find Contours  

        ` List<MatOfPoint> contours = new ArrayList<MatOfPoint>();`
        ` Mat hierarchy = new Mat();`
        ` Imgproc.findContours(dilate_im, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);`  
3.  Approximate contours to polygons and get bounding rectangle for each digit.
        For example: To find Contours  

        `  MatOfPoint2f approxCurve = new MatOfPoint2f(); //Convert contours(i) from MatOfPoint to MatOfPoint2f;  MatOfPoint2f contour2f = new MatOfPoint2f( mContours.get(i).toArray() );`
        
        `  //Processing on mMOP2f1 which is in type MatOfPoint2f   double approxDistance = Imgproc.arcLength(contour2f, true)*0.03;//0.02;   Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);    //Convert back to MatOfPoint   MatOfPoint points = new MatOfPoint( approxCurve.toArray() );`
       
        `   // Get bounding rect of contour    Rect rect = Imgproc.boundingRect(points);`
                
####Creating Training Data   
1. Resize the cropped digits to (50,50) and convert it into floating point (32FC1) and reshape it to (1,1) Matrix  

        `  Mat trainData = new Mat(); Mat response_array = new Mat();`
        `  img = Highgui.imread("path of training image"); img.convertTo(img, CvType.CV_32FC1); Size dsize = new Size(50, 50); Imgproc.resize(img, img, dsize);  img.convertTo(img, CvType.CV_32FC1);`
        `  imgResized = img.reshape(1, 1);   trainData.push_back(imgResized);`
        `  response_array.push_back(new Mat (1,1,CvType.CV_32FC1,new Scalar(i)));`
        `  Mat response = new Mat(); Mat tmp; tmp=response_array.reshape(1,1); //make continuous  `
        `  tmp.convertTo(response,CvType.CV_32FC1); // Convert  to float`  
        `  CvKNearest knn = new CvKNearest();knn.train(trainData, response);`

#### Storing and Accessing Training Data
  1. For Storing training data in bitmap file, first Convert 32FC1 trainData matrix type to 8UC1 and store it in BMP file using Utils.matToBitmap. For example, to store training samples  

        `  Bitmap bmpOut1 = Bitmap.createBitmap(trainData.cols(), trainData.rows(), Bitmap.Config.ARGB_8888);`
        ` trainData.convertTo(trainData,CvType.CV_8UC1);  Utils.matToBitmap(trainData, bmpOut1);`
        `  File file = new File(path);    file.mkdirs();  File file = new File(path, "train.jpg");  OutputStream fout = null;`
        `  try {  fout = new FileOutputStream(file);  BufferedOutputStream bos = new BufferedOutputStream(fout);  bmpOut1.compress(Bitmap.CompressFormat.JPEG, 100, bos); bos.flush();  bos.close();  bmpOut1.recycle();  } `
        ` catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {e.printStackTrace();}`
                          
2. For Accessing training data in BMP file,   

        `  BitmapFactory.Options o = new BitmapFactory.Options(); o.inScaled = false;`
        ` Bitmap blankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.train,o);`
        `  int trainWidth = blankBitmap.getWidth(); int trainHeight = blankBitmap.getHeight(); Mat trainData2 = new Mat();`
        `  Utils.bitmapToMat(blankBitmap,trainData2);  Mat trainData3 = new Mat(); `
        ` Imgproc.cvtColor(trainData2,trainData2,Imgproc.COLOR_BGRA2GRAY); // 1. change the number of channels; trainData2.convertTo(trainData3,CvType.CV_32FC1);  CvKNearest knn2 = new CvKNearest();  knn2.train(trainData3, response3);`
                            
#### Issues with OpenCV on Android
1. No FileStorage class to write and read XML files from Mat Objects  
2. Edit the ASynchronousJavaHelper.class file in OpenCV library to run the application on Android 5 versions

### Network Administration and Service Hosting
The basic duties in Network Administration for this project are to provide a common Network API for server – client communication. The basic requirement is to provide secured HTTPS connection over TCP between server and client. 

#### Java XML Web Services (JAX-WS)

JAX-WS web services serve the purpose of secured HTTP communication between server and client. The main advantage of web services is, they can facilitate inter platform communication, i.e. a server (can be of Java, C, Python etc.,) can communicate with a client of any programming platform and vice versa. 

Since we have a java server on Linux box, we hosted the required web service using Apache Tomcat 7, a java based web-hosting engine. The published web service will be in a XML format, called as Web Service Description Language (WSDL) can be accessed [here](https://elcloudo.tamu.edu:8443/SLSComm/commhandler?wsdl). The clients (can be a Java, Android etc.,) get their implementation files by parsing this published WSDL into their own platform. 

#### Apache Tomcat 7

As mentioned above this is a Java based web-hosting engine which is quite different from Apache. To host an HTTPS connection on Tomcat 7 we need too have a SSL security file mainly .kestore file generated by java keystore program. The basic way to generate the .keystore can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/wiki/ssl). Modifying the server.xml file on server as shown in [here](http://www.mkyong.com/tomcat/how-to-configure-tomcat-to-support-ssl-or-https/) can now make Tomcat 7 support HTTPS. 

#### Web Service

The server side code responsible for necessary database transactions is integrated with the web service files and a .war file is generated to facilitate hosting on Tomcat 7. The deployed/published web services can now serve the purpose of secured network connection between the server and client. The complete source code for server side code integrated with web services can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/nranudeep1990/Project3/SLSComm).

#### Log4j

Log4j API is used to log the errors that can occur on hosted web service. These logs are logged on to the Scoutout.log file on the server.

#### Client side network API

As already specified above, any programming platform developer can use our web services to develop their clients.

* Code for Java HTTPS Web Services client can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/nranudeep1990/Project3/WebServiceClient).
* Code for Android HTTPS Web Services client can be found [here](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/jacano23/GoogleSignIn/app/src/main/java/com/ecen489/slidermenu/WebServiceHttps.java). 

#### Issues

1. Initially we faced lot of issues regarding HTTPS, but were solved by following the steps discussed above.
2. To make SqLite db file writable from the Tomcat 7 we need to provide write permissions to the directory  in which the file resides to facilitate creation of backup file (in plain words) before writing on to it.
3. Android HTTPS connection gave lot of authentication issues which we bypassed by modifying the Trust manager to allow all HTTPS connections.



## Data Integrity and Information Management

#### Team Members
* Ian DeGroot

#### Overview

The primary goal of the data integrity and information team is to define the format of the data that is sent between the clients and the server. This was accomplished by defining the library used, deciding what messages should be sent, and writing java code to wrap and unwrap the messages.

#### Library
 
JSON messages were chosen to contain the information being sent across the network because it is easy to wrap and unwrap, while still containing
enough information for all of the messages being passed. The JSON.Simple library is used by this application.

#### Messages

After defining the library used, the messages that are passed were defined. After many discussions with members from different teams six different client messages,
along with their responses by the server, were decided upon. These messages are: Login, Logout, Add Friends,  Add Check In, Get Recent Friends, and Get Recent Locations. 
These messages, along with the responses, can be found in their JSON formatting [here](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Project3/JSONMessages.md). 

#### Java Wrapping/Unwrapping

Using these set messages, java code was written for the wrapping and unwrapping of each of these JSON messages. The code is found [here]
(https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/iandegroot/Project3JSONInterface). This is used to generate JSON objects
on the client side, and to unwrap these same JSON objects on the server so that the data can be accessed. It is also utilized for server-to-client messages. 

## Database Engine and Storage

Team Members:
* Evelyn Cifuentes
* Mandel Oats

## Security and Authentication

Team Members:
* Trevor Dennis
* Nagaraj Janakiraman

####Overview:
The primary goal of the Security and Authentication team is to make sure that the connection between our Android Clients and Server is authenticated. The authentication is designed to have two steps. The first step is the authentication between Google and our Clients and the second step is the authentication between Clients and the Server. 

####Step1: Google – Client Authentication:
In the first step of the authentication process, the client is designed to use Google+ credentials to login to user's Google account using OAuth 2.0 protocol. The GoogleApiClient libraries were used to setup this process. Once the connection between Client and Google is authenticated by Google, `onConnected()` method is reached where the second step of a authentication starts.

####Step2: Client – Server Authentication:
Once the user logs-in through Google+ sign-in procedure, the app gets access to user's personal information. The username is extracted from this information on Google+ and a hashed password of the user is sent to the server as JSON wrapped messages over HTTPS. The Server is designed to compare the hashed password and username across the database and authenticates the connection by sending back a “Session-ID” to the client. Every time the client wants to send a request to the server, the Session-ID is also sent along with it. The server validates the Session-ID before processing the requests from Client. This makes the client – server connection authenticated to certain extent. Once the user logs out of the google+ account on the client, a logout message is sent to the server with a valid Session-ID. If the Session-ID is valid, the server then resets the Session-ID on the database and recreates a new one when login command is received again.

####Integration of Google+ sign-in features and Extracting Friends
The first step in this process is to create a project in the Google Developer Console and generate OAuth 2.0 Client credentials. This process requires a `SHA1` key and a package name. Once the project is created, the app is ready to use Google APIs and send queries to Google. This app uses GoogleApiClient libraries to implement Google+ features. There are three important callback interfaces that were implemented in this application.

* `ConnectionCallbacks`: This callback implements `onConnected()`method that is called when google authenticates the user. All the user information can be fetched inside this method using the Google Plus libraries.
*  `OnConnectionFailedListener`: This interface implements `onConnectionFailed()` method that can be used to perform tasks that are to be done after the connection authentication fails.
* `ResultCallback<LoadPeopleResult>` : This interface implements `onResult()` method where the information about the user’s friends can be extracted.

A consent screen pops out when the user tries to login using Google+. This provides the user with the ability to give access to particular friend circles. The Google+ People API has a method called `loadConnected()` that can extract friends list of the user who are using this app. This helps us to filter out all unwanted information regarding friends.


## Account Managment and Administration

Team Members:
* Randy Neal

#### Overview

The account management and administration division is responsible for maintaining the user accounts. To accomplish this, 
a program has been developed to facilitate the seamless addition or deletion of users and locations. This is much more user 
friendly and straight forward than using SQLite commands from the Ubuntu terminal and is also not dependent on the Firefox plugin.

#### System Admin Program

The program is written in Java and is built upon Jswing and SQLite. The concept was to build a simple program that is easy to use, 
while implementing the basic functions of MYSQL and the SQLite plugin in Firefox. The PhysicAmigo System Admin Program consists of 
a main window that has a connect and exit button, a list of tables on the left, and the data of the selected table. Once the program 
is run, the only functions that are enabled are the connect and exit buttons. 

#####Connection

The connection is established via the connect button. The administrator clicks the buttona, which brings up a credentials window and 
utilizes a set user name and password to login. If the either item is entered incorrectly, the administrator will be notified via a 
Joption dialog and will be required to re-enter the credentials. Once the connection is established, the logic of program communicates 
to other classes to close the credentials window. The tables list will be shown once the credentials is closed.

#####Tables List

The tables list shows the two tables in the database that are open to editing. The system administrator is only concerned with adding or 
removing users or locations. As such, these are the only tables that will appear. If the need arises, more tables can be implemented and 
shown in the list. The modular design of the application allows the easy modification and addition of more information. Once the table is 
selected in the list, either with the arrows/enter keys or with the mouse, the table data is then displayed automatically in the center 
window.

#####Table Data

Table data is not editable directly in the data panel. To modify the table, the administrator must select the appropriate table which will
then enable the add/delete user or location buttons. Once the table is modified, the data is refreshed automatically, allowing any additions
or deletions are immediately visible.

#####Add/Delete User

The add/delete user buttons only become available once the users table is selected. Once clicked, the add user button brings up a new window 
that has some text fields for user ID, user name, session ID, and password. The session ID and password are hashed using the standard hashing 
code implemented in the other parts of the project. If a mistake is made the administrator can delete all the data in the fields using the 
clear data button. If the info is correct, the administrator simply clicks the enter button and the user is added. To delete a user, the delete 
user button is clicked and the same functionality is implemented, however the system administrator just needs to note the number in the table 
that corresponds to that user and enters it into the field. Once enter button is clicked, the user is deleted both from the users table and from 
any corresponding entries in the friends table.

#####Add/Delete Location

Similar to users buttons, he add/delete location buttons only become available once the locations table is selected. The only data needed to add 
locations is the name of the location, the latitude, and longitude. Again, once the enter button is clicked, the table is updated. To delete a 
location, the same process is used; enter the int of the row and click the enter button to delete the location.

## Special Projects

Team Members:
* George Sullivan
* Jeffery Jensen
* Hong Pan

## Business and Finance

Team Members:
* Blade Roybal
* Yajie Zeng

## Public Relations and Web Presence

Team Members:
* Benito Ramirez Jr.