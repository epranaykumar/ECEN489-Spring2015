Multithreading is a very important (and complicated) programming concept.  This wiki page will try to break down how threading works in Java.

***

# Runnable Objects and Threads

Let's start by looking at how you can create and run a thread in the first place.

A class that can run in a thread must implement the _Runnable_ interface and contain a _void run()_ override function.  Here's an example class:

`class ExampleObject() implements Runnable {`

`...`

`@override`

`void public run() {`

`...`

You create and declare this object like usual, then create a Thread object to wrap around it.  Note that Thread is a separate object and must be handled separately from foo:

`ExampleObject foo = new ExampleObject();`

`Thread fooThread = new Thread(foo);`

`fooThread.start();`

Note that there are **TWO** ways to execute the run() class in a Runnable object.  You can call `fooThread.start()` or `fooThread.run()`.  Even though you created a thread object, **ONLY** `fooThread.start()` will run the code in a separate line of execution.  `fooThread.run()` will execute the code in the same line like a regular method call.


Bottom line, you want to use `fooThread.start()`.

***

# Objects and References

Now that we know how to create and start a thread, let's take a step back and look at how objects relate to each other.  Here's a simple object declaration in Java:

`ExampleObject foo = new ExampleObject();`

This creates an Object called foo of type ExampleObject.  The class that runs this line of code (let's call it ParentClass) knows what foo is and can run foo's public methods:

`foo.doSomething();`

![One way class connection](http://www.redcylindersoftware.com/489/oneway.jpg)

Note that foo can't interact with ParentClass.  If ParentClass creates a hundred foos, it would have to look at each one in turn to check for execution status, variable values, or whatever the code is written to do.  In a multithreaded program, this is inefficient.  A better option is to pass a reference to ParentClass on to foo, like so:

`ExampleObject foo = new ExampleObject(this);` <-- this is a reference to ParentClass

To support this, ExampleObject's construction would need to be modified slightly:

***

`class ExampleObject() {`

   `ParentClass parent;` <-- Field where we store the reference to ParentClass

   `ExampleObject(ParentClass parent) {`

      this.parent = parent; <-- In the constructor, set the value of parent so foo can talk to it
`...`

![Two way class connection](http://www.redcylindersoftware.com/489/twoway.JPG)

Now, if foo needs something from ParentClass, it can go to the ParentClass methods directly.  This is more efficient in a multithreaded program.

***

# Thread Safety

What makes multithreading challenging is ensuring thread safety.  If you create a runnable object whose `run()` function just executes a few simple calculations and ends, there is no problem.  However, if your threads run some kind of loop, steps must be taken to ensure that the threads don't get stuck in them.  If you want to see what happens (and crash your computer), check out the [Endless Threading Demo](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/I-Love-Github/EndlessThreadingDemo).

Here are some guidelines when it comes to ensuring thread safety:

* Make the final line in every run() method a command to notify the parent that this thread is done
* Ensure all parents have references to their children (Vectors and ArrayLists are good for this)
* Do not remove references to a child object until it has reached the end of its run() function
* Add a public boolean field to the child objects.  Use that field as a guaranteed fail-safe to end a loop
* Make use of sleep/notify or exception handling to manage your child threads (not covered here)

To see an example of how to properly control threads, check out the [Safe Threading Demo](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/I-Love-Github/SafeThreadingDemo).

***

# Data Safety

Problems with data pop up in multithreaded programs when there are multiple threads working with the same variable, file, or collection.  The easiest way to overcome this is by using **synchronize** blocks.  Synchronize blocks act as a bottleneck.  All threads trying to enter such a block must stop and take turns, ensuring that one thread doesn't try to change data that another one is working with.

Synchronize can have the scope of a variable or a method.  For blanket protection, you can declare an entire method as synchronized, but this is very inefficient:

`synchronize public void kungFoo() {`

Alternatively, you can declare a synchronize block around a variable, this locks that variable for the duration of the code block, preventing other threads from modifying it:

`synchronized(fooVariable) { ...perform kungFoo... }`


***

# Helpful Links

[Java Concurrency Tutorial] (http://docs.oracle.com/javase/tutorial/essential/concurrency/)

[Java Thread API] (http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html)

[Tutorialspoint Multithreading Tutorial] (http://www.tutorialspoint.com/java/java_multithreading.htm)

[Javabeginner.com Tutorial] (http://www.javabeginner.com/learn-java/java-threads-tutorial)

[Youtube Tutorial: Multithreading in Java Part 1 | Introduction to Threads in Java] (https://www.youtube.com/watch?v=O_Ojfq-OIpM)