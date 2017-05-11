# **Intro to Arduino**

An Arduino is a type of microcontroller that was developed to be used as a teaching tool. It comes with it's own IDE (Integrated Development Environment) which is used to write sketches (mix of C/C++) for the arduino. Arduino the company was started in 2005 in Italy as a project for students at the Interaction Design Institute Ivrea in Ivrea, Italy. The fact that the boards are cheap, open-source, and run on easy to learn code make them great for learning to work with microcontrollers or DIY projects. There are many different types of Arduino boards that have slightly different specs but this tutorial will use the Arduino Uno, which is shown below.

![Arduino Uno](http://cangurocode.com/cangurocode/wp-content/uploads/2014/04/ArduinoUno.png)

## Getting started with the Arduino Uno

### Downloading the IDE:

To download the IDE and all the software that you will need to start developing simply:

* Go to [arduino.cc/en/Main/Software](arduino.cc/en/Main/Software)
* Choose your OS from the box on the right
* Wait for the download to complete and run the executable file

### Getting familiar with the IDE:

The IDE for the Arduino boards is very simple and consists only of a menu, a small toolbar, a text editor, and a console. The first time you use the IDE make sure you configure it with the correct board and COM port. Instructions for this are given below.

* To set the board that you are working with go to Tools > Board and select the correct board
* To set the COM port go to Tools > Port and select the COM port your board is using
	
NOTE: The com port used by the board can be found by plugging the board in and navigating to the Device Manager on Windows

![Arduino IDE](https://majdsrour.files.wordpress.com/2012/08/arduino_ide.jpg)

The buttons in the toolbar will be explained going from left to right:

* The Check button checks through your code and lets you know if there are any errors
* The Upload (Right arrow) button compiles your code and loads it onto the board
* The New (Blank sheet) button creates a new sketch
* The Open (Up arrow) button opens a list of all the sketches in your sketchbook
* The Save (Down arrow) button saves your sketch
* The Serial Monitor (Magnifying glass) button opens the serial monitor

More about the IDE: [Arduino IDE Guide](arduino.cc/en/Guide/Environment)

### Your First Sketch (Blink):

This sketch will blink an on-board LED, and can also be used to blink a real LED.

To begin, open up the IDE and go to File > Examples > 01.Basics > Blink or just copy the code from below. The Blink sketch should pop up in a new window. As you can see, the code is divided up into two functions: setup and loop. These functions will always be in an Arduino sketch and substitute for a main function in C++ or Java. The setup function will always run only once when the program starts and is used to initialize pins and other values. The loop function acts as an infinite loop that will continue to loop as long as the program is running.

Here's the code for the Blink sketch:

NOTE: Comments are the same as those in Java or C/C++

```
// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin 13 as an output.
  pinMode(13, OUTPUT);
}

// the loop function runs over and over again forever
void loop() {
  digitalWrite(13, HIGH);   // turn the LED on (HIGH is the voltage level)
  delay(1000);              // wait for a second
  digitalWrite(13, LOW);    // turn the LED off by making the voltage LOW
  delay(1000);              // wait for a second
}
```

In the code above we have our setup and loop functions as explained earlier. Inside the setup function we set the pinMode of pin 13 (which is a physical pin on the board) to act as an output. If we were reading in signals with this pin we would set the pinMode to INPUT. Once this is completed the loop function is entered. THe first digitalWrite sends a logic signal HIGH to pin 13, this will turn the LED on. The arguement for the delay function is in milliseconds so after pin 13 is written HIGH the pogram will wait for 1 second. After this delay the second digitalWrite sends a logic signal LOW to pin 13, this will turn off the LED. The program then waits for another second. As this functionality is in the loop function the light will continue to blink indefinitely.

NOTE: The Arduino boards, and most microcontrollers, may have one program loaded onto them at a time. Once the program is loaded that program will continue to run until a different program is loaded onto the board.

Good job! The LED on the board will now blink at 1 second intervals. If you would like to make a larger LED blink use the diagram below to hook it up.

NOTE: The resistor shown should have a value of 1K ohm.

![](http://4.bp.blogspot.com/-bSwcT88QabE/UVuMY29lHfI/AAAAAAAAAAw/wyLijXEmafk/s1600/01+Blinky_bb.png)

### Your second sketch (Serial Output):

For the purposes of debugging it's always nice to have some way to print out to the screen. This can be done using the Serial.print or Serial.println functions and the serial monitor. First modify the code from the first example by adding in the print functions shown below:

```
// the setup function runs once when you press reset or power the board
void setup() {
  // initialize serial communication at 9600 bits per second
  Serial.begin(9600);
  
  // initialize digital pin 13 as an output.
  pinMode(13, OUTPUT);
}

// the loop function runs over and over again forever
void loop() {
  digitalWrite(13, HIGH);   	// turn the LED on (HIGH is the voltage level)
  Serial.println("LED is on");	// write to the serial monitor
  delay(1000);              	// wait for a second
  digitalWrite(13, LOW);    	// turn the LED off by making the voltage LOW
  Serial.println("LED is off");	// write to the serial monitor
  delay(1000);              	// wait for a second
}
```

Make sure the correct PORT is chosen, if you are having trouble with the PORT number go back to the section of the tutorial where that is discussed or google your problem, and click the magnifying glass to bring up the serial port once the program is running. You should see the statements being printed to the monitor.

For more information on printing to the serial monitor check out this example: [Serial Print Help](http://arduino.cc/en/Tutorial/DigitalReadSerial)

## Ping Tutorial

[Ping Example](http://arduino.cc/en/tutorial/ping)

## More information

Getting started with Arduino: http://arduino.cc/en/Guide/HomePage

Example sketches: http://arduino.cc/en/Tutorial/HomePage

Language reference and help: http://arduino.cc/en/Reference/HomePage

More useful information: http://playground.arduino.cc/