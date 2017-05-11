# Android Studio
The official IDE for Android Application development is based on IntelliJ IDEA.
Additional features found in this IDE include (not limited to):
* Gradle-based build system
* Code Templates
* Layout Editor that supports drag and drop theme editing
* Pro Guard (app-signing capabilities)

### Download Information
1. Make sure your computer has the latest Java SE ([JDK](http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html)) installed.  
    
2. For Windows 8 users go to Control Panel -> System -> Advanced system settings -> Environment Variables and add **JAVA_HOME** as a new system variable that points to your JDK folder.

3. Download [Android Studio](http://developer.android.com/sdk/index.html) and install any additional packages from the SDK manager that you need.

### Android Studio Tour
#### Android Project Structure
###### Your App has two main entities:  
1. What you want your app to do 
2. How you want your app to look 

Android Studio has a distinct way of managing these two entities. One are your JAVA files, the other
are your XML files.

![Image] (http://developer.android.com/images/tools/studio-helloworld-design.png)

* Java
    * Can be found under the src/main/java directory
    * Controls the intent of an activity
* Resources 
    * Found under the res folder
    * *Drawable* folders that hold images
    * *Layout* folder with XML that represents the screen designs
    * *Menu* folder with XML of the items that will appear on the Action Bar
    * *Values* folder with XML containing dimensions, strings, and styles

App components are the essential building blocks of an Android App. Each component is a different point through which the system can enter your app. There are four different type of app components. Each type serves a distinct purpose and has a distinct lifecycle that defines how the component is created and destroyed. Here are the four types of app components:  

##### App Components
* Activities
* Services
* Content Providers
* Broadcast Receivers

#### The Manifest File
Before the system can start an any app component, the system must know that component exists by reading the app's AndroidManifest.xml file. Your app must declare all its components in this file, which must be at the root of the app project directory.  

The Manifest does a number of things in addition to declaring the app's components, such as:  
* Identify any user permission the app requires, such as Internet access or read-access to the user's contacts.
* Declare the minimum API Level required by the app, based on which APIs the app uses.
* Declare hardware and software features used or required by the app, such as the camera, bluetooth services, or a multi-touch screen.
* API libraries the app needs to be linked against such as the Google Maps library.

##### Declaring Components
The primary task of the manifest is to inform the system about the app's components. For example, a manifest file can declare an activity as follows:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ... >
    <application android:icon="@drawable/app_icon.png" ... >
        <activity android:name="com.example.project.ExampleActivity"
                  android:label="@string/example_label" ... >
        </activity>
        ...
    </application> 
</manifest>
```
In the `<application>` element, the `android:icon` attribute points to resources for an icon that identifies the app.  
In the `<activity>` element, the `android:name` attribute specifies the fully qualified class name of the Activity subclass and the `android:label` attributes specifies a string to use as the user-visible label for the activity.  

You must declare all app components this way:
* `<activity>` elements for activities
* `<service>` elements for services
* `<receiver>` elements for broadcast receivers
* `<provider>` elements for content providers
 
##### Declaring component capabilities
You can use an `Intent` to start activities, services and broadcast receivers. The way the system identifies the components that can respond to an intent is by comparing the intent received to the intent filters provided in the manifest file. 
 
You can declare an intent filter for your component by adding an `<intent-filter>` element as a child of the component's declaration element.  

For example, if you built an email app with an activity for composing a new email, you can declare an intent filter to respond to "send" intents (in order to send a new email) like this:

```xml
<manifest ... >
    ...
    <application ... >
        <activity android:name="com.example.project.ComposeEmailActivity">
            <intent-filter>
                <action android:name="android.intent.action.SEND" />
                <data android:type="*/*" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```  
Then, if another app creates an intent withe `ACTION_SEND` action and pass it to `startActivity()`, the system may start your activity so the user can draft and send an email.

##### Declaring app requirements
Try to clearly define what features are needed by your app by declaring it in the manifest file. That way devices that lack a certain feature cannot install your app. For example, if your app requires a camera and uses APIs introduced in API level 7, you should declare these as requirements in your manifest file:

```xml
<manifest ... >
    <uses-feature android:name="android.hardware.camera.any"
                  android:required="true" />
    <uses-sdk 
android:minSdkVersion="7" android:targetSdkVersion="19" />
    ...
</manifest>
```
Now, devices that do not have a camera and have and Android API lower than 7 cannot install your app.

##### Testing
* Can utilize a virtual device from the AVD manager
* Can use an android device

### Helpful Tips
1. Under Help->Default Keymap References, you can find all available keyboard shortcuts.
2. To display line numbers, right click on left hand side of your code and select show line numbers

### Helpful Links
* [Android Studio Tutorial](http://developer.android.com/tools/studio/index.html)
* [The New Boston](https://www.thenewboston.com/videos.php?cat=278)
* [Application Fundamentals|Android] (http://developer.android.com/guide/components/fundamentals.html)

