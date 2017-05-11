# WifiManager in Android


Applications developed on Android can have access to the state of Wireless connections at very low level, these applications can have access to almost all functionalities of a WiFi Connection.

The information that an application can access includes connected network's link speed,IP address, negotiation state, other networks information. Applications can also scan, add, save, terminate and initiate WiFi connections.

This WifiManager class object can be instantiated using following syntax  
`WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);`

WifiManager class has three nested classes in it  
* [WifiManager.MulticastLock](http://developer.android.com/reference/android/net/wifi/WifiManager.MulticastLock.html) Allows an application to receive Wifi Multicast packets.
* [WifiManager.WifiLock](http://developer.android.com/reference/android/net/wifi/WifiManager.WifiLock.html)  Allows an application to keep the Wi-Fi radio awake.
* [WifiManager.WpsCallback](http://developer.android.com/reference/android/net/wifi/WifiManager.WpsCallback.html) Interface for callback invocation on a start WPS action .

   
This `wifimanager` object can access many methods that can provide almost all details as mentioned in second paragraph. List of some common syntax's are as follows   
`To get BSSID :: wifiManager.getConnectionInfo().getBSSID()`   
`To get RSSID :: wifiManager.getConnectionInfo().getRssi()`   
`To get MAC ID:: wifiManager.getConnectionInfo().getMacAddress()`  
`To reconnect :: wifiManager.reconnect()`  
`To disconnect:: wifiManager.disconnect()`  
`To get List of available networks :: wifiManager.startScan()`

Here to get the list of available networks i.e to read the startScan() of wifimanager results one should take care to register the BroadcastReceiver, which can be done using register method with scanreceiver class object and Intent as an input arguments. The syntax for the same will be as follows.  
`class WifiScanReceiver extends BroadcastReceiver { //This class can be nested class inside your Activity class`  
`public void onReceive(Context c, Intent intent) { //This method is used to read the scan results`  
`}`  
`}`  
`The following syntax can be called in onCreate() method of the activity class`  
`WifiScanReceiver wifiReciever = new WifiScanReceiver();`  
`registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));`  

The list returned by this `startScan()` method of wifimanger is of ScanResult type. The information in these list objects can be accessed as follows:  
`List<ScanResult> wifiList = wifimanager.startScan();`  
`(wifiList.get(1)).toString(); //converts data of ScanResult() to String`  

Let us have a small example code that uses this WifiManager class to get list of available networks.  
`package com.example.tungala.tutorialwifimanager;`

`import android.annotation.SuppressLint;`  
`import android.content.BroadcastReceiver;`  
`import android.content.Context;`  
`import android.content.Intent;`  
`import android.content.IntentFilter;`  
`import android.net.wifi.ScanResult;`  
`import android.net.wifi.WifiManager;`  
`import android.support.v7.app.ActionBarActivity;`  
`import android.os.Bundle;`  
`import android.view.Menu;`  
`import android.view.MenuItem;`  
`import android.widget.ArrayAdapter;`  
`import android.widget.ListView;`  
`import android.widget.ScrollView;`

`import java.util.List;`


`public class MainActivity extends ActionBarActivity {`

    WifiManager wifiManager;
    NetworkScanner scanner;
    ListView listView;

    String[] networks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        scanner = new NetworkScanner();
        wifiManager.startScan();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    protected void onPause() {
        unregisterReceiver(scanner);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(scanner, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class NetworkScanner extends BroadcastReceiver {
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = wifiManager.getScanResults();
            networks = new String[wifiScanList.size()];
            for(int i = 0; i < wifiScanList.size(); i++){
                networks[i] = ((wifiScanList.get(i)).toString());
            }

            listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,networks));

        }
    }


We need to implement some user permissions in the Maifest.XML to permit our app to have certain WIFI access permissions.  
`<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`  
`<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />`  

![WiFi App](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/nranudeep1990/img.png)

## References
[http://www.tutorialspoint.com/android/android_wi_fi.htm](http://www.tutorialspoint.com/android/android_wi_fi.htm)  
[http://developer.android.com/reference/android/net/wifi/WifiManager.html](http://developer.android.com/reference/android/net/wifi/WifiManager.html)  
[http://developer.android.com/reference/android/net/wifi/WifiManager.MulticastLock.html](http://developer.android.com/reference/android/net/wifi/WifiManager.MulticastLock.html)  
[http://developer.android.com/reference/android/net/wifi/WifiManager.WifiLock.html](http://developer.android.com/reference/android/net/wifi/WifiManager.WifiLock.html)  
[http://developer.android.com/reference/android/net/wifi/WifiManager.WpsCallback.html](http://developer.android.com/reference/android/net/wifi/WifiManager.WpsCallback.html)
