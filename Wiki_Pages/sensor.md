
##Sensors Overview
Android devices have a wide variety of sensors that can capture measurements about the device and its environment. There are three broad categories of sensors supported by the Android platform:  
1. Motion sensors: These sensors measure acceleration forces and rotational forces along three axes. This category includes accelerometers, gravity sensors and gyroscope sensors  
2. Environmental sensors: These sensors measure various environmental parameters, such as pressure and 
   illumination.  
3. Position sensors: These sensors measure the physical position of a device. This category includes orientation sensors and magnetometers.  

[To know more about the sensortypes supported by the Android platform click on this link](http://developer.android.com/guide/topics/sensors/sensors_overview.html). This tutorial is about how to acquire raw sensor data from the physical sensors in our applications.

## Sensor Framework

To integrate sensors in our application, we require three classes and one interface from the android.hardware package:  
* SensorManager: It's the system service that manages sensors. Applications get a reference to the sensor manager by calling `getSystemService` method by passing the argument `CONTEXT.SENSOR_SERVICE`. For example,
                 

* Sensor:  This class creates an instance of a specific sensor. It provides various methods that determine a sensor's capabilities like `getResolution(),getMaximumRange(),getPower(), getVendor(), getVersion()`.These methods are generally used to determine the data interpretation algorithms that are applied on the raw sensor data.  

* SensorEvent: Sensor readings are represented as instances of the `SensorEvent` class. The data this class holds depends on the specific kind of sensor that generated the reading but will include the sensor type, time stamp, accuracy, measurement data associated with that new reading.  

* SensorEventListener: If an Application wants to receive information from a sensor, then it will have to implement a sensor event listener. This interface defines Call back methods: `OnAccuracyChanged` method and `onSensorChanged`method that are invoked when accuracy of a sensor changes or when the sensor has a new reading resp.

In order for an application to use sensors, it needs to follow certain sequence of steps:  

1. First, the application needs to refer SensorManager by calling `getSystemService` method and passing in the argument CONTEXT.SENSOR_SERVICE. For example   
           `sm =(SensorManager) getSystemService(SENSOR_SERVICE);`

2. Using that SensorManager class, find out whether the required sensor exists or not. One can do this by using the `getDefaultSensor` method in `SensorManager` class . For example   `
 
         ` if (sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){   `
            // Success! There is ACCELEROMETER.    
            // get reference to Accelerometer    
            sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
            }   
          else{ 
        // failure! There's no ACCELEROMETER  
        finish();  
        }`

3. If that type of sensor exists, get a instance of that sensor by using `Sensor` class. For example,    
   `sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);`
 
4. Then register a listener for that sensor which waits for the SensorEvent to happen and callsback two methods: `onSensorChanged` method passing in the `SensorEvent` class corresponding to the new reading and `OnAccuracyChanged` method passing in the sensor that changed and the new accuracy  
  * To register a listener, first implement the main class of the application with `sensoreventlistener` interface. For example  
        `public class MainActivity extends ActionBarActivity implements SensorEventListener {`
  * Then register a listener by calling the `registerListener` method. This registration is generally done in the `onResume` method rather than in `onCreate` method to save power.  
`@Override`    
  `protected void onResume() { `  
    `super.onResume();`  
     `sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);`  
`}`
 
  * Then unregister the listener in `onPause` and `onStop` methods  
   `protected void onPause() {`  
      `super.onPause();`  
      `sm.unregisterListener(this);`  
   ` }`
5. In the two callback methods: `onSensorChanged` and `OnAccuracyChanged` methods, first verify whether the event is from the associated sensor or not.  
  * If yes, then use Event class that holds the raw data from the sensor along with other information like timestamp for the data interpretation algorithms or you can directly use the raw data depending on the purpose of the application.  
 For example,  
`@Override`  
    `public void onAccuracyChanged (Sensor arg0, int arg1) {`  
        `// Inflate the menu; this adds items to the action bar if it is present.`  
   ` }`  
    `@Override`  
    `public void onSensorChanged (SensorEvent event) {`  
  
        `// first we need to check the event corresponds to accelerometer reading`  
       ` if(event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){`  
  
            `displayReading.setText(" X: "+event.values[0]+"\n Y: "  
             +event.values[1]+"\n Z: "+event.values[2]); `
  
        `}`
  
## Sample Code
1. [For applications to view list of sensors available on a device ](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/epranaykumar/ViewSensorList/app/src/main/java/com/example/pranaykumar/viewsensorlist) 
2. [For applications to read raw accelerometer readings ]( https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/epranaykumar/Accelerometer/app/src/main/java/com/example/pranaykumar/accelerometer)  
  
## References
1. To know about the data interpretation algorithms applied on the raw sensor data, go through the 'professional_android_sensor_programming' book written by google developers  
2. <http://developer.android.com/guide/topics/sensors/sensors_overview.html>  
3. For a sample code, <http://davidcrowley.me/?p=356>
4. For applying low pass and high pass filters on accelerometer readings, <https://www.youtube.com/watch?v=tY-NImDTOz4>

   