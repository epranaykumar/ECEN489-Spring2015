# LocationManager #

 The basic concepts involved in accessing the current geographical location(latitude, longitude) from a GPS enabled android device will be discussed in this tutorial. 
LocationManager class provides access to the system location services. These services allow applications to obtain periodic updates of the device's geographical location, or to fire an application-specified `Intent` when the device enters the proximity of a given geographical location.

 The instance of this class is generally retrieved through
  `Context.getSystemService(Context.LOCATION_SERVICE)` 


## Important Public Methods and Constants ##

1. `String GPS_PROVIDER` is a constant that has the name of the GPS location provider.

2. `String NETWORK_PROVIDER` is a constant that has the name of the network location provider. 

3. `public Location getLastKnownLocation (String provider)`
 * Returns a Location indicating the data from the last known location fix obtained from the given provider.
 * This can be done without starting the provider. Note that this location could be out-of-date, for example if the device was turned off and moved to another location.
 * If the provider is currently disabled, null is returned.

4. `public LocationProvider getProvider (String name)`
 * Returns the information associated with the location provider of the given name, or null if no provider exists by that name.
 * Note that this method returns an object of the class `LocationProvider`. This object can be used to get information about the location service provider. Some example methods that `LocationProvider` class implements are `getPowerRequirement()`, `hasMonetaryCost()`, `requiresCell()`, `requiresNetwork()` etc.

5. `public boolean isProviderEnabled (String provider)`
 * Returns the current enabled/disabled status of the given provider.
 * The result from this method can be used to alert the user to enable GPS.

6. `public void requestLocationUpdates (String provider, long minTime, float minDistance, LocationListener listener)`
 * `provider` - the name of the provider with which to register
 * `minTime`  - minimum time interval between location updates, in milliseconds
 * `minDistance` - minimum distance between location updates, in meters
 * `listener` - a LocationListener whose onLocationChanged(Location) method will be called for each location update

7. `public void setTestProviderLocation (String provider, Location location)`
 * Sets a mock location for the given provider.
 * This location will be used in place of any actual location from the provider. The location object must have a minimum number of fields set to be considered a valid LocationProvider Location, as per documentation on Location class.

8. `public void addProximityAlert (double latitude, double longitude, float radius, long expiration, PendingIntent intent)`
 * Set a proximity alert for the location given by the position (latitude, longitude) and the given radius.
 * When the device detects that it has entered or exited the area surrounding the location, the given PendingIntent will be used to create an Intent to be fired
 
## LocationListener Interface ##

 `LocationListener` is a public interface used for receiving notifications from the `LocationManager` when the location has changed. The following methods are called if the `LocationListener` has been registered with the location manager service using the `requestLocationUpdates (String provider, long minTime, float minDistance, LocationListener listener)` method.

1. `public abstract void onLocationChanged (Location location)` - called when the location has changed
2. `public abstract void onProviderDisabled (String provider)` - called when the provider is disabled by the user. If the requestLocationUpdates is called on an already disabled provider, this method is called immediately.
3. `public abstract void onProviderEnabled (String provider)` - called when the provider is enabled by the user.
4. `public abstract void onStatusChanged (String provider, int status, Bundle extras)` - called when the provider status changes. This method is called when a provider is unable to fetch a location or if the provider has recently become available after a period of unavailability.

##Permissions##
 The following two permission need to added to the `AndroidManifest.xml` file to access the GPS services.
 * ` <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> `
 * ` <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />`
 
## Example Android App ##

### Activity ###

    package com.nagaraj.locationmanagertutorial;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.TextView;

    public class GeolocationActivity extends Activity {
    TextView latitudeText;
    TextView longitudeText;
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);
        latitudeText=(TextView)findViewById(R.id.latitude_text);
        longitudeText=(TextView)findViewById(R.id.longitude_text);
        LocationManager locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener= new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

    }
    private void gpsAlert(){
    AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(context);
    alertDialogBuilder.setTitle("GPS Alert!");
    alertDialogBuilder
            .setMessage("GPS disabled! Press 'Go to Settings' to enable.")
            .setCancelable(true)
            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, Redirect to GPS Settings
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),    
                    0);
                }
            })
            .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close the dialog box and do nothing
                    dialog.cancel();
                }
            });
    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();

    }
    private class MyLocationListener implements LocationListener{
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            latitudeText.setText(Double.toString(latitude));
            longitudeText.setText(Double.toString(longitude));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
       gpsAlert();
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geolocation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    }

## Screenshots of the app ##
   ![Screenshot -1](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/LocationManagerTutorial/rsz_locationmanager-3.png)

   ![Screenshot -2](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/LocationManagerTutorial/rsz_locationmanager2.png)

   ![Sreenshot-3](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/LocationManagerTutorial/rsz_locationmanager1.png)


## References ##

* [LocationManager - Android developer Website](http://developer.android.com/reference/android/location/LocationManager.html)

* [Tutorials Point - Location based services](http://www.tutorialspoint.com/android/android_location_based_services.htm)

* [Geolocation tutorial - Youtube](https://www.youtube.com/watch?v=7-n6p6RxSS8)