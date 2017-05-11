![BlueTooth](http://www.bluetooth.com/style%20library/Bluetooth/smallLogo.jpg) 
##What is Bluetooth?
Bluetooth is the name given to the wireless technology invented by Ericsson of Sweden in 1994; and is named after Danish King Harald Blotan (Harold Bluetooth). The whole concept of the technology revolves around the desire to eliminate the hassle of using RS-232 cables/connections. At the present there are literally billions of devices from computer peripherals to automobiles and is currently being utilized by the military for field communications, helmet cameras, and flight operations. As of 1998 Bluetooth is owned by a collection of companies (known as the Bluetooth Special Interest Group "SIG" which includes Ericsson, IBM, Intel, Microsoft, Nokia, and Toshiba), which collectively work to promote, preserve, and teach the technology. 
####Bluetooth Specifications:
* Bluetooth technology features a small form-factor and utilizes low power: approximately 1/100th of wifi.
* At the heart of it, Bluetooth is a short range radio that uses a [spread spectrum](http://en.wikipedia.org/wiki/Spread_spectrum), [frequency hopping](http://en.wikipedia.org/wiki/Frequency-hopping_spread_spectrum), [full duplex](http://en.wikipedia.org/wiki/Duplex_%28telecommunications%29) signal which operates in an unlicensed ISM band at 2.4-2.485 GHz at a rate of approximately 1600 hops/second. 
* Transfers data at rate of approximately 1MB/second.
* The maximum range for the Bluetooth radio is nominally 100 meters. (Realistically its closer to about 60)  

---
####Bluetooth and Java
#####Important: You must get the appropriate API's for Java. Also, ensure that the API matches your system, there are different ones for MacOS, Linux, and for both X86 and X64 Windows platforms.
The examples in this tutorial utilize the BlueCove API's. They can be found at the [BlueCove website.] (http://www.bluecove.org/)
This picture illustrates just how BlueCove interfaces with Java, the various components of the system, and the Bluetooth hardware.
![BlueTooth](http://www.bluecove.org/images/stack-diagram.png) 
######Step 1: Discover the Device
The LocalDevice class defines the basic functions of the Bluetooth manager. The Bluetooth manager provides the lowest level of interface possible into the Bluetooth stack. It provides access to and control of the local Bluetooth device.  
The DiscoveryAgent class provides methods to perform device and service discovery. A local device must have only one DiscoveryAgent object. This object must be retrieved by a call to getDiscoveryAgent() on the LocalDevice object. There are two ways to discover devices. First, an application may use startInquiry() to start an inquiry to find devices in proximity to the local device. Discovered devices are returned via the deviceDiscovered() method of the interface DiscoveryListener. The second way to discover devices is via the retrieveDevices() method. This method will return devices that have been discovered via a previous inquiry or devices that are classified as pre-known. (Pre-known devices are those devices that are defined in the Bluetooth Control Center as devices this device frequently contacts.) The retrieveDevices() method does not perform an inquiry, but provides a quick way to get a list of devices that may be in the area.
The DiscoveryAgent class also encapsulates the functionality provided by the service discovery application profile. The class provides an interface for an application to search and retrieve attributes for a particular service. There are two ways to search for services. To search for a service on a single device, the searchServices() method should be used. On the other hand, if you don't care which device a service is on, the selectService() method does a service search on a set of remote devices.  
Example:    
 
    LocalDevice localDevice = LocalDevice.getLocalDevice(); //Gets the local Bluetooth Device  
            DiscoveryAgent agent = localDevice.getDiscoveryAgent(); //Starts the DiscoveryAgent on the local device  
            agent.startInquiry(DiscoveryAgent.GIAC, listener); //Starts an inquiry to find other devices in range

More information regarding the DiscoveryAgent class and associated methods is available [here.] (http://bluecove.org/bluecove/apidocs/javax/bluetooth/DiscoveryAgent.html)
######Step 2: Connect to Remote Device
To connect, some information regarding the device is required.
The RemoteDevice class represents a remote Bluetooth device. It provides basic information about a remote device including the device's Bluetooth address and its friendly name. 
More information regarding the RemoteDevice class and associated methods is available [here.] (http://www.bluecove.org/bluecove/apidocs/javax/bluetooth/RemoteDevice.html)   
Example:

    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass arg1) { //Gets the name or address of the remote devices
        String name;
        try {
            name = btDevice.getFriendlyName(false);
        } catch (Exception e) {
            name = btDevice.getBluetoothAddress();
        }
        devices.add(btDevice);
        System.out.println("device found: " + name); //Prints the names of the discovered devices
    }
The ServiceRecord interface describes characteristics of a Bluetooth service. A ServiceRecord contains a set of service attributes, where each service attribute is an (ID, value) pair. A Bluetooth attribute ID is a 16-bit unsigned integer, and an attribute value is a DataElement. The structure and use of service records is specified by the Bluetooth specification in the Service Discovery Protocol (SDP) document. Most of the Bluetooth Profile specifications also describe the structure of the service records used by the Bluetooth services that conform to the profile. An SDP Server maintains a Service Discovery Database (SDDB) of service records that describe the services on the local device. Remote SDP clients can use the SDP to query an SDP server for any service records of interest. A service record provides sufficient information to allow an SDP client to connect to the Bluetooth service on the SDP server's device.
ServiceRecords are made available to a client application via an argument of the DiscoveryListener.servicesDiscovered(int, javax.bluetooth.ServiceRecord[]) method of the DiscoveryListener interface. ServiceRecords are available to server applications via the method LocalDevice.getRecord(javax.microedition.io.Connection) on LocalDevice. More information regarding the ServiceRecord interface and associated methods is available [here.] (http://www.bluecove.org/bluecove/apidocs/javax/bluetooth/ServiceRecord.html)   
Example:  

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        for (int i = 0; i < servRecord.length; i++) {
            String bt_Address = servRecord[i].getConnectionURL(ServiceRecord.AUTHENTICATE_ENCRYPT, true);  //Here the addresses for the devices are found
            if (bt_Address == null) {
                continue;
            }
            DataElement serviceName = servRecord[i].getAttributeValue(0x0100);  //The service names are found
            if (serviceName != null) {
                System.out.println("service " + serviceName.getValue() + " found " + bt_Address);
                if(serviceName.getValue().toString().startsWith("OBEX Object Push")){   //Check to see if the service is OBEX
                    sendMessageToDevice(bt_Address);
                }
            } else {
                System.out.println("service found " + bt_Address);  //Prints the address of the service
            }
        }
    }
To connect to the Bluetooth Client, a ClientSession is used to connect with the remote device and the BlueCove interface HeaderSet is used to determine if the connection is good.
The HeaderSet interface defines the methods that set and get the values of OBEX headers. 
More information regarding the ClientSession class and associated methods is available [here.] (http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/bluetooth/jsr082/javax/obex/ClientSession.html)   
Example:

    try{
            System.out.println("Connecting to " + bt_Address);
            ClientSession clientSession = (ClientSession) Connector.open(bt_Address); //Here the connection is opened using the address of the remote device
            HeaderSet hsConnectReply = clientSession.connect(null); //Connection is complete
            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) { //Check to see if Server response is wrong
                System.out.println("Failed to connect");
                return;
            }


######Step 3: Send Data
Once the connection is established the data needs to be sent. 
For this tutorial, Object Exchange (OBEX) is used for the file transfer. ​OBEX is a transfer protocol that defines data objects and a communication protocol two devices can use to exchange those objects. OBEX is designed to enable devices supporting infrared communication to exchange a wide variety of data and commands in a resource-sensitive standardized fashion. More information on OBEX can be found [here.](https://developer.bluetooth.org/TechnologyOverview/Pages/OBEX.aspx)  
First we need to create the HeaderSet for the object that we are sending, next the headers of the HeaderSet need to be set using .setHeader(HeaderSet.[value from this table](http://www.bluecove.org/bluecove/apidocs/javax/obex/HeaderSet.html), "name of the value").   
Next we create a putOperation and an outputStream to send the data over.     
Example:

    //The following lines create the HeaderSet and sets the headers
            HeaderSet hsOperation = clientSession.createHeaderSet();
            hsOperation.setHeader(HeaderSet.NAME, "ServerProperties.txt"); //Sets the name of the object to "ServerProperties.txt
            hsOperation.setHeader(HeaderSet.TYPE, "text");     //Sets the type of info in the header
            //Create PUT Operation and output stream
            Operation putOperation = clientSession.put(hsOperation);
            OutputStream os;

The OutputStream can now be used as usual to send the data using .write(), .flush(), etc.

---
####Bluetooth and Android Studio
#####Important: You must include the Bluetooth permissions in AndroidManifest.XML, otherwise the application will shut down.  
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
    <uses-permission android:name="android.permission.BLUETOOTH"/>
######The android.bluetooth Package
Provides classes that manage Bluetooth functionality, such as scanning for devices, connecting with devices, and managing data transfer between devices. The Bluetooth API supports both "Classic Bluetooth" and Bluetooth Low Energy.   
For more information about Classic Bluetooth, see the Bluetooth guide. For more information about Bluetooth Low Energy, see the Bluetooth Low Energy (BLE) guide.  
The Bluetooth APIs let applications:
   * Scan for other Bluetooth devices (including BLE devices).
   * Query the local Bluetooth adapter for paired Bluetooth devices.
   * Establish RFCOMM channels/sockets.
   * Connect to specified sockets on other devices.
   * Transfer data to and from other devices.
   * Communicate with BLE devices, such as proximity sensors, heart rate monitors, fitness devices, and so on.
   * Act as a GATT client or a GATT server (BLE).   
To perform Bluetooth communication using these APIs, an application must declare the BLUETOOTH permission. Some additional functionality, such as requesting device discovery, also requires the BLUETOOTH_ADMIN permission.   

For this small tutorial program, some of the device's Bluetooth parameters will be showcased using the following classes: 
  * BluetoothManager - High level manager used to obtain an instance of an BluetoothAdapter and to conduct overall Bluetooth Management.  
  * BluetoothAdapter - Represents the local device Bluetooth adapter.  
  * Other classes can be found [here.](http://developer.android.com/reference/android/bluetooth/BluetoothAdapter.html)  

After calling the BluetoothAdapter class, attributes can now be obtained or set (if admin permission has been enabled) using the following methods:
  * .getName() - gets the friendly name of the adapter (string)
  * .isEnabled() - checks to see if the adapter is enabled (boolean)
  * .getAddress() - gets the MAC address of the adapter (string)
  * .getState() - gets the state of the adapter (int)
  * .getScanMode() - gets the scan mode of the adapter (int)
  * .getBondedDevices() - gets the addresses of any bonded device (set)
  * setName(String name) - Set the friendly Bluetooth name of the local Bluetooth adapter
  * startDiscovery() - Start the remote device discovery process (boolean) 
  * others can be found [here.](http://developer.android.com/reference/android/bluetooth/BluetoothAdapter.html)

---
######Code can be found:
[Here.](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/gmnealusn/Bluetooth%20Tutorial%20Code)
---
####References
History and Specifications: [Bluetooth.com](http://www.bluetooth.com/Pages/Fast-Facts.aspx)

BlueCove API's, Libraries, and Code References: [BlueCove.org](http://www.bluecove.org/)

OBEX information: [Bluetooth.com](https://developer.bluetooth.org/TechnologyOverview/Pages/OBEX.aspx)

Bluetooth Android Package Info: [Android Studio Developers](http://developer.android.com/reference/android/bluetooth/package-summary.html)



---
######Special Thanks to:
George Sullivan for assistance with coding problems.