# Android Services

Android services are background processes.  They are designed to handle long-term or continuous operations that would cause the user interface to hang (the primary activity thread is also the UI thread).  They can be 

## Lifecycle

Services can run persistently or as one-shot functions.  They can interact with no activities/classes, or they can talk to several at a time.  This makes their lifecycle and implementation more complicated.  Here are some flowcharts that represent three possible service lifecycles:

### 1: Activity calls startService()
![](http://www.redcylindersoftware.com/489/services1.png)

### 2: Activity calls bindService()
![](http://www.redcylindersoftware.com/489/services2.png)

### 3: Activity calls startService(), then binds to the service with bindService()
![](http://www.redcylindersoftware.com/489/services3.png)

## Communication

Two ways of communicating with services are through Intents and Messages -- this wiki will not cover these methods, but they are well documented in the Android API.

For our purposes, the most convenient way of interacting with a service is by **binding** our activity to it using bindService().  This gives our activity access to that service's public methods.  If we pass a reference of our activity to the service, we get direct two-way communication between the service and our activity.

Always call **onUnbind()** and **stopService()** in the **onStop()** method of your activity.  This ensures that services do not run persistently and waste user resources.  Eventually, the Android system will remove the service to free up resources, but we're responsible adults and should not rely on the system to handle our garbage collection.

## Persistence

Services created with **onBind()** only persist as long as activities are bound to it.  Once all activities are unbound, it will self-destruct.

Calling **stopService()** from an activity or **stopSelf()** from within the service will also cause it to self-destruct.

Services created with **onStart()** will persist until the system destroys it to free up resources.  If this is not desired, there are three levels of persistence that can be used during the creation process:

**START_NOT_STICKY**: Once the service is destroyed, it will not restart automatically.  It is up to the activity to recreate it.

**START_STICKY**: Once the service is destroyed, the system will automatically restart it and call **onStartcommand()**.  This will allow the service to effectively run forever.

**START_REDELIVER_INTENT**: This wiki is not covering intent-driven services, but this persistence option redelivers the last intent in addition to restarting it whenever it is destroyed.

# Android Manifest

Just like activities, services must be declared in the AndroidManifest.xml file:

    <manifest ... >
      ...
      <application ... >
          <service android:name=".ExampleService" />
          ...
       </application>
    </manifest>

# Sample Code

If you're just here for some sample code.  Refer to [this example project](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/I-Love-Github/Demos-and-Research/HelloBluetooth) to see a working example of how services work.

# Helpful Links
[Android API: Services](http://developer.android.com/guide/components/services.html)
[Android API: Bound Services](http://developer.android.com/guide/components/bound-services.html)
[Android API: Messages](http://developer.android.com/reference/android/os/Message.html)
[Stackoverflow: Getting an Android Service to Communicate With an Activity](http://stackoverflow.com/questions/2463175/how-to-have-android-service-communicate-with-activity)
[Stack Overflow: How to Get an Android Bound Service to Survive a Configuration Restart](http://stackoverflow.com/questions/16169488/how-to-get-an-android-bound-service-to-survive-a-configuration-restart)