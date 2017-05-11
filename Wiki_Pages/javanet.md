# A tutorial on Networking in JAVA

## What is network programming?

The term network programming refers to writing programs that execute across multiple devices (computers), in which the devices are all connected to each other using a network.

The java.net package of the J2SE APIs contains a collection of classes and interfaces that provide the low-level communication details, allowing you to write programs that focus on solving the problem at hand.

The java.net package provides support for the two common network protocols:

**TCP:** TCP stands for Transmission Control Protocol, which allows for reliable communication between two applications. TCP is typically used over the Internet Protocol, which is referred to as TCP/IP.

**UDP:** UDP stands for User Datagram Protocol, a connection-less protocol that allows for packets of data to be transmitted between applications.

Before going into the details of socket programming we will see a small explanation of programming concepts of java which includes **try-catch-finally** blocks and **threading**. This makes our socket programming quite easy to understand and code.

To start with the try-catch-finally we should know that methods in java classes will generate known errors or exceptions and it will be specified as throws exception after the method and its arguments.

`public void connectToServer() throws IOException`

So whenever we are dealing with these types of methods we should nest these methods in try block. There will also a roundabout for these and it is placing throws exception after our method as shown above.

**Try block** – This block facilitates the smooth error handling in Java by nesting in the methods that throws exceptions.

**Catch block** – This block catches the exceptions thrown by the methods and performs the logic defined in it.

**Finally block** – This block contains the logic that needs to executed even in case of exceptions before the program terminates.

Syntax: 

      `try {
			Methods that can throw exceptions and the logic to implement them
		} catch(Exception e) {
		} finally {
			Logic that should be implemented for sure;
                }`


**Threading:**
		Threading is a concept which allows us to instantiate many java objects simultaneously and run the methods simultaneously.

A thread can be started by start() function and run() method should be defined if a class implements thread.

## Socket Programming:
***

Sockets provide the communication mechanism between two computers using TCP. A client program creates a socket on its end of the communication and attempts to connect that socket to a server.

When the connection is made, the server creates a socket object on its end of the communication. The client and server can now communicate by writing to and reading from the socket.

The _java.net.Socket_ class represents a socket, and the _java.net.ServerSocket_ class provides a mechanism for the server program to listen for clients and establish connections with them.

### What exactly it takes to establish communication between a client and server?
***


The following steps occur when establishing a TCP connection between two computers using sockets:

* The server instantiates a ServerSocket object, denoting which port number communication is to occur on.

    `ServerSocket listener = new ServerSocket(9000);`

* The server invokes the accept() method of the ServerSocket class. This method waits until a client connects to the server on the given port.

    `listener.accept()`
* After the server is waiting, a client instantiates a Socket object, specifying the server name and port number to connect to. 

    `Socket socket = new Socket(serverAddress, 9000);`
* The constructor of the Socket class attempts to connect the client to the specified server and port number. If communication is established, the client now has a Socket object capable of communicating with the server.

         `in = new BufferedReader(`
          `new InputStreamReader(socket.getInputStream()));`
          `out = new PrintWriter(socket.getOutputStream(), true);`

* On the server side, the accept() method returns a reference to a new socket on the server that is connected to the client's socket.

* This socket object returned by the accept method is transferred to a thread to facilitate multiple client connections on to the server.

    `new ServerThread(listener.accept(), clientNumber++).start();`

* After the connections are established, communication can occur using I/O streams. Each socket has both an OutputStream and an InputStream. The client's OutputStream is connected to the server's InputStream, and the client's InputStream is connected to the server's OutputStream.

* TCP is a two way communication protocol, so data can be sent across both streams at the same time. There are following usefull classes providing complete set of methods to implement sockets.

* To close the communication the socket has to be closed using close() method.

   `socket.close();`

***
The _**ServerSocket**_ class has four constructors:
***


**1. public ServerSocket(int port) throws IOException**

* _Attempts to create a server socket bound to the specified port. An exception occurs if the port is already bound by another application._

**2. public ServerSocket(int port, int backlog) throws IOException**

* _Similar to the previous constructor, the backlog parameter specifies how many incoming clients to store in a wait queue._

**3. public ServerSocket(int port, int backlog, InetAddress address) throws IOException**

* _Similar to the previous constructor, the InetAddress parameter specifies the local IP address to bind to. The InetAddress is used for servers that may have multiple IP addresses, allowing the server to specify which of its IP addresses to accept client requests on_

**4. public ServerSocket() throws IOException**

* _Creates an unbound server socket. When using this constructor, use the bind() method when you are ready to bind the server socket_

* _If the ServerSocket constructor does not throw an exception, it means that your application has successfully bound to the specified port and is ready for client requests._

***
Here are some of the common methods of the _**ServerSocket**_ class:
***

**1. public int getLocalPort()**

* _Returns the port that the server socket is listening on. This method is useful if you passed in 0 as the port number in a constructor and let the server find a port for you._

**2. public Socket accept() throws IOException**

* _Waits for an incoming client. This method blocks until either a client connects to the server on the specified port or the socket times out, assuming that the time-out value has been set using the setSoTimeout() method. Otherwise, this method blocks indefinitely_

**3. public void setSoTimeout(int timeout)**

* _Sets the time-out value for how long the server socket waits for a client during the accept()._

**4. public void bind(SocketAddress host, int backlog)**

* _Binds the socket to the specified server and port in the SocketAddress object. Use this method if you instantiated the ServerSocket using the no-argument constructor._

* _When the ServerSocket invokes accept(), the method does not return until a client connects. After a client does connect, the ServerSocket creates a new Socket on an unspecified port and returns a reference to this new Socket. A TCP connection now exists between the client and server, and communication can begin._

***
**Socket Class Methods:**
***


_The java.net.Socket class represents the socket that both the client and server use to communicate with each other. The client obtains a Socket object by instantiating one, whereas the server obtains a Socket object from the return value of the accept() method._

The _**Socket**_ class has five constructors that a client uses to connect to a server:


**1. public Socket(String host, int port) throws UnknownHostException, IOException.**

* _This method attempts to connect to the specified server at the specified port. If this constructor does not throw an exception, the connection is successful and the client is connected to the server._

**2. public Socket(InetAddress host, int port) throws IOException**

* _This method is identical to the previous constructor, except that the host is denoted by an InetAddress object._

**3. public Socket(String host, int port, InetAddress localAddress, int localPort) throws IOException.**

* _Connects to the specified host and port, creating a socket on the local host at the specified address and port._

**4. public Socket(InetAddress host, int port, InetAddress localAddress, int localPort) throws IOException.**

* _This method is identical to the previous constructor, except that the host is denoted by an InetAddress object instead of a String_

**5. public Socket()**

* _Creates an unconnected socket. Use the connect() method to connect this socket to a server._

When the Socket constructor returns, it does not simply instantiate a Socket object but it actually attempts to connect to the specified server and port.

Some **_methods of interest in the Socket class_** are listed here. Notice that both the client and server have a Socket object, so these methods can be invoked by both the client and server.



**1. public void connect(SocketAddress host, int timeout) throws IOException**

* _This method connects the socket to the specified host. This method is needed only when you instantiated the Socket using the no-argument constructor._

**2. public InetAddress getInetAddress()**

* _This method returns the address of the other computer that this socket is connected to._

**3. public int getPort()**

* _Returns the port the socket is bound to on the remote machine._

**4. public int getLocalPort()**

* _Returns the port the socket is bound to on the local machine._

**5. public SocketAddress getRemoteSocketAddress()**

* _Returns the address of the remote socket._

**6. public InputStream getInputStream() throws IOException**

* _Returns the input stream of the socket. The input stream is connected to the output stream of the remote socket._

**7. public OutputStream getOutputStream() throws IOException**

* _Returns the output stream of the socket. The output stream is connected to the input stream of the remote socket_

**8. public void close() throws IOException**

* _Closes the socket, which makes this Socket object no longer capable of connecting again to any server_

***
**InetAddress Class Methods:**
***


This class represents an Internet Protocol (IP) address. Here are following usefull methods which you would need while doing socket programming:


**1.static InetAddress getByAddress(byte[] addr)**

* _Returns an InetAddress object given the raw IP address ._

**2. static InetAddress getByAddress(String host, byte[] addr)**

* _Create an InetAddress based on the provided host name and IP address._

**3. static InetAddress getByName(String host)**

* _Determines the IP address of a host, given the host's name._

**4. String getHostAddress()**

* _Returns the IP address string in textual presentation._

**5. String getHostName()**

* _Gets the host name for this IP address._

**6. static InetAddress InetAddress getLocalHost()**

* _Returns the local host._

**7. String toString()**

* _Converts this IP address to a String._

_**Reference**_ [Java Tutorials Point](http://www.tutorialspoint.com/java/java_networking.htm)

