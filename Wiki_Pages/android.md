# Android Architecture

As Android developers, it is important to have at least a basic understanding of the structure of the Android operating system. This understanding can help you find your way through the many layers of code that are available to you through the Android Open Source Project source tree.  This tutorial will include a brief summary of the main layers of the architecture along with a few examples of where you may see elements of each layer implemented.


# Android Architecture Layers
There are five main layers of building blocks that make up the Android operating system.
![Image](http://www.alliance-rom.com/attachments/android-system-architecture-jpg.9989/)

## Linux Kernel

The whole Android OS is built on top of the Linux Kernel with some further architectural changes.  Based on Linux version 2.6, the kernel provides preemptive multitasking, low-level core system services such as memory, process and power management in addition to providing a network stack and all of the necessary device drivers for hardware such as the device display, Wi-Fi, Bluetooth, audio, etc.

The Linux kernel also acts as an abstraction layer between the hardware and other software layers, and since the Android operating system is built on such a popular and trustworthy foundation, the programming Android to variety of different devices is now a relatively painless task.

## Libraries

The next layer is Android’s native libraries. It is this layer that enables the device to handle different types of data to send through the Linux Kernel to command the hardware to perform specific functions. 

**android.opengl** – A Java interface to the OpenGL ES 3D graphics rendering API.

**android.os** – Provides applications with access to standard operating system services including messages, system services and inter-process communication.

**android.media** – Provides classes to enable playback of audio and video.

**android.net** – A set of APIs providing access to the network stack. Includes android.net.wifi, which provides access to the device’s wireless stack.

**android.provider** – A set of convenience classes that provide access to standard Android content provider databases such as those maintained by the calendar and contact applications.

**android.text** – Used to render and manipulate text on a device display.

**android.util** – A set of utility classes for performing tasks such as string and number conversion, XML handling and date and time manipulation.

This just names a few of the Android native libraries, and does not include any of the core Java libraries.

    package com.example.android.basicnetworking;
    import android.content.Context;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;
    import android.util.TypedValue;
    import android.view.Menu;
    import android.view.MenuItem;
    import com.example.android.common.logger.Log;
    import com.example.android.common.logger.LogFragment;
    import com.example.android.common.logger.LogWrapper;
    import com.example.android.common.logger.MessageOnlyLogFilter;

## Android Runtime
Android Runtime consists of Dalvik Virtual machine and Java Core libraries.

### Dalvik Virtual Machine

Each application used on the Android platform runs withing its own instance of a DVM.  It is a type of JVM used to run apps and is optimized for low processing power and low memory environments. 

Running applications in virtual machines provides a number of advantages. First, applications are unable interfere (intentionally or otherwise) with the operating system or other applications, nor can they directly access the device hardware. Second, this level of abstraction makes applications platform neutral in that they are never tied to any specific hardware.

Unlike the JVM, the Dalvik Virtual Machine doesn’t run .class files, instead it runs .dex files. .dex files are built from .class file at the time of compilation and provide increased efficiency in low resource environments. The DVM allows multiple instances to be created simultaneously providing security, isolation, memory management and threading support. 

![Trivia: The Dalvik Virtual Machine was developed by Dan Bornstein of Google.](https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-9/10984134_956045854406995_3259677526604846412_n.jpg?oh=e4d765c9d244ad93f11df970575a0f3e&oe=5557F0B7&__gda__=1435721022_488820a5ce4d337fd804519d0d5bd42b)

### Application Framework
These are the blocks that our applications directly interacts with. These programs manage the basic functions of phone like resource management, voice call management, etc. As a developer, consider these as some basic tools with which we are building our applications.

Important blocks of Application framework are:

ActivityManager: Manages the activity life cycle of applications

Content Providers: Manage the data sharing between applications

Telephony Manager: Manages all voice calls. We use telephony manager if we want to access voice calls in our application.

Location Manager: Location management, using GPS or cell tower

Resource Manager: Manage the various types of resources we use in our Application

     private void checkNetworkConnection() {
      // BEGIN_INCLUDE(connect)
      ConnectivityManager connMgr =
          (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
      if (activeInfo != null && activeInfo.isConnected()) {
          wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
          mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
          if(wifiConnected) {
              Log.i(TAG, getString(R.string.wifi_connection));
          } else if (mobileConnected){
              Log.i(TAG, getString(R.string.mobile_connection));
          }
      } else {
          Log.i(TAG, getString(R.string.no_wifi_or_mobile));
      }
      // END_INCLUDE(connect)
    }
### Applications
Applications are the top layer in the Android architecture.  This is where you're every-day Android mobile device user is going to stay, and within this layer users can perform just about any function they need their device to perform.  
![Image] (https://litmus.com/blog/wp-content/uploads/2013/05/email-app-icons.jpg)



![](https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10983426_956045861073661_2113936045128816933_n.jpg?oh=5c1e7824aa1706e839f0557ed15048b9&oe=55573602&__gda__=1432108648_7e9219289e31f6cf7efba3cbad720377)

![](https://scontent-a-dfw.xx.fbcdn.net/hphotos-xfp1/v/t1.0-9/14178_956045881073659_8537007433163667675_n.jpg?oh=d95b1883c2fbe37ce050dad0ff20310c&oe=55606926)

![](https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10985537_956045924406988_1784657017020250117_n.jpg?oh=9d8052fe96ad0136ebb590a4915c2fac&oe=555B543E&__gda__=1430960212_c5576a976cb3f8a4ada092308fe2a537)

![](https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10985537_956045924406988_1784657017020250117_n.jpg?oh=9d8052fe96ad0136ebb590a4915c2fac&oe=555B543E&__gda__=1430960212_c5576a976cb3f8a4ada092308fe2a537)

## Sources and Further Reading
http://developer.android.com/samples/index.html
https://source.android.com/devices/#
http://www.techotopia.com/index.php/An_Overview_of_the_Android_Architecture
http://developer.android.com/reference/packages.html