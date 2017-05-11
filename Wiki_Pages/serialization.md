# Serialization in JAVA ##
----
Java provides a mechanism called object serialization to represent an object as a sequence of bytes that includes the object's data as well as information about the object's type and types of data stored in object.

After a serialized object has been saved to a file or transported on a network to sockets of other machines, it can be read from the file or socket bound to the incoming port and deserialized. Deserialization means that the type information and bytes that represent the object and its data can be used to recreate the object in memory. 

*Note:* The entire process is JVM independent, meaning an object can be serialized on one platform and can be deserialized on an entirely different platform.  
  
### java.io.Serializable interface  
Serializable is a marker interface(has no body) and is used to mark java classes which support serializing capability. It must be implemented by the class whose object you want to persist.
 
**Syntax:** ` public class [ClasssName] implements Serializable { . . .}` 

Here is an example classs [Cars](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/Cars.java) that has Serializing capability.
 
-----  
## Classes and Methods:  
The methods for serializing and deserializing an object is contained in classes **ObjectOutputStream** and **ObjectInputStream** respectively.

### ObjectOutputStream class ###
The ObjectOutputStream class contains several useful methods. Some important methods and their description follows.

* `public ObjectOutputStream(OutputStream out) throws IOException {}` is a constructor that creates an ObjectOutputStream that writes to the specified OutputStream.
* `public final void writeObject(Object x) throws IOException {}` writes the specified object to the ObjectOutputStream.
* `public void flush() throws IOException {}` flushes the current output stream.
* `public void close() throws IOException {}` closes the current output stream.

**Example:**
Here is an example program [WriteCars.java](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/WriteCars.java) that serializes two objects of Cars class and stores them to the binary file [Carlist.bin](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/Carlist.bin).

### ObjectInputStream class ###

ObjectInputStream deserializes objects and primitive data written using an ObjectOutputStream. This class contains several useful methods. Some commonly used methods and their description follows.

* `public ObjectInputStream (InputStream in) throws IOException {} ` is a constructor that creates an ObjectInputStream that reads from specified InputStream.
* `public final Object readObject() throws IOException, ClassNotFoundException{}` reads an object from the input stream
* `public void close() throws IOException {} ` closes ObjectInputStream.

**Example:**
Here is an example program [ReadCars.java](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/ReadCars.java) that deserializes two objects of Cars class from the binary file [Carlist.bin](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/Carlist.bin) and prints them on screen.

###Transient Keyword###
We can prevent some data members of a Serializable class from getting Serialized by prefixing the keyword `transient` when defining the instance variables.

**Example:**
Here is a set of example programs that explain the use of `transient` keyword. The class [CarsTransient](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/CarsTransient.java) is a modified version of class `Cars.java` with the `Model` data member defined as `transient`. [WriteCarsTransient.java](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/WriteCarsTransient.java) and [ReadCarsTransient.java](https://github.com/CourseReps/ECEN489-Spring2015/blob/master/Students/tjnagaraj/SerializationDemo/src/com/company/ReadCarsTransient.java)   
have the same functionality as `WriteCars.java` and `ReadCars.java`, but they operate on the objects of `CarsTransient.java` class. 
On running the `WriteCarsTransient.java` followed by `ReadCarsTransient.java`, we see that `Model` datatype displayed as `NULL` in both the recreated objects. This clearly explains the use of the `transient` keyword.

### Serialization of an object via Client/Server Connection ###
**Example:**
The project [SerializationNetworking](https://github.com/CourseReps/ECEN489-Spring2015/tree/master/Students/tjnagaraj/SerializationNetworking/src/com/company) has three classes- `Cars.java`, `Client.java` and `Server.java`. `Cars.java` is the same class we had used for the first two examples. `Server.java` hosts a Server at PORT:8000. `Client.java` implements a client that gets connected to the Server, gets input of details about a car(Make,Model,Year) from the user, and serializes the object containing these details and sends it across the network to the Server. Server receives the serialized object, deserializes it and prints the details on Screen. 


##References##
* [Tutorial- Java T point](http://www.javatpoint.com/serialization-in-java)
* [Tutorial - tutorialspoint](http://www.tutorialspoint.com/java/java_serialization.htm)
* [The Java Tutorials - ORACLE](http://docs.oracle.com/javase/tutorial/jndi/objects/serial.html)
* [Youtube- Java Serializable Interface (Video Tutorial)](https://www.youtube.com/watch?v=YzwiuRDgSSY)
* [Serializing an Object via a Client/Server Connection](http://www.developer.com/design/article.php/10925_3597071_2/Serializing-an-Object-via-a-ClientServer-Connection.htm)