# What is JSON?
JSON stands for JavaScript Object Notation. 

It’s used to store information in an organized manner of name and value pairs and for interchanging information. It is lightweight and easily read by humans and computers. Also, since it’s just plain text, it is able to be used with a variety of different languages.

JSON is used to send data from the server to the client in web applications. Very much like XML, but simpler. 
Ajax is what you normally use to transfer JSON data. JSON has become important through the rise of Ajax, since it is important for sites to be able to load data quickly and asynchronously. AJAX stands for asynchronous JavaScript and XML. With Ajax, web applications can send data to and retrieve from a server in the background without having to reload the page. 

JSON is Built on two structures:
-A collection of name/value pairs. 
-An ordered list of values. 

# Writing JSON to store data
## For writing name/value pairs
First, create an object using a variable and enclose the variable’s value in curly braces. The value is then an object. 
Inside that object, you can declare properties using the syntax “name”: “value”. This creates pairs of properties and you separate the properties by commas. 

    var restaurant = {
       "name" : "McDonalds",
       "location": "University Dr",
       "type" : "American"
    };

There is now information that is stored under the restaurant variable.
To retrieve the information stored in the variable we refer to the name of the property we need. In the format: **VariableName.PropertyName**.

   ` document.write('The restaurant is on' restaurant.location); //Prints The restaurant is on University Dr`
    `document.write('Food type is' restaurant.type); //Prints Food type is American`

## For writing arrays
If we decide to store more than one restaurant under the same variable, we can create an array. All we do to create the array is enclose the objects in the array with square brackets and separate the array elements with commas. 
    
    var restaurants = [{
	    "name" : "McDonalds",
	    "location": "University",
	    "type" : "American"
    },
    {
	    "name" : "Olive Garden"
	    "location" : "University"
	    "type" : "Italian"
    }];

Array indexing starts at 0. To access the information, it would look like this:

    document.write(restaurants[0].name); //Prints McDonalds
    document.write(restaurants[1].location); // Prints University
    document.write(restaurants[2].type); //Prints Chinese

### For nesting data
In my opinion, this is the easier way to store multiple objects. Simply because it’s easier to understand when retrieving the information. 
All you do, is nest the objects as shown. 


    var restaurants = {
	    "McDonalds": {
		    "name" : "McDonalds",
		    "location": "University",
		    "type" : "American"
	    },

	    "OliveGarden": {
		    "name" : "Olive Garden"
		    "location" : "University"
		    "type" : "Italian"
	    }
    };

Accessing this information is easy to read since it describes exactly what you're trying to read.

    document.write(restaurants.McDonalds.type); //Prints American
    document.write(restaurants.OliveGarden.type); //Prints Italian

# Data types supported by JSON
* Number
* String
* Boolean
* Array
* Value
* Object
* Whitespace
* null

#JavaScript and JSON

First, I want to call attention to how closely related the notation between JSON and JavaScript is.

###JSON:
    {
      "orderID": 12345,
      "shopperName": "John Smith",
      "shopperEmail": "johnsmith@example.com",
      "contents": [
        {
          "productID": 34,
          "productName": "SuperWidget",
          "quantity": 1
       },
        {
          "productID": 56,
          "productName": "WonderWidget",
          "quantity": 3
        }
      ],
      "orderCompleted": true
    }

In the code above, we have an object with many name/value pairs.
Contents is an array with two objects, each with three properites.

Now, from that JSON string we can create a JavaScript object by simply changing a few things. This shows how simple it is.

###JavaScript Object:
    <script type="text/javascript">
    var cart = {
      "orderID": 12345,
      "shopperName": "John Smith",
      "shopperEmail": "johnsmith@example.com",
      "contents": [
        {
          "productID": 34,
          "productName": "SuperWidget",
          "quantity": 1
        },
        {
          "productID": 56,
          "productName": "WonderWidget",
          "quantity": 3
        }
      ],
      "orderCompleted": true
    };
    </script>


##Creating a JSON String 

JSON.stringify() is a method that JavaScript has to makes the JavaScript variable into a JSON string with the variable's data. 
Using the code from eariler, we add:

    alert ( JSON.stringify( cart ) ); 

after the variable. The JavaScript will look like this:

    <script type="text/javascript">
 
    var cart = {
      "orderID": 12345,
      "shopperName": "John Smith",
      "shopperEmail": "johnsmith@example.com",
      "contents": [
        {
          "productID": 34,
          "productName": "SuperWidget",
          "quantity": 1
        },
        {
          "productID": 56,
          "productName": "WonderWidget",
          "quantity": 3
        }
      ],
      "orderCompleted": true
    };
 
    alert ( JSON.stringify( cart ) ); 
 
    </script>

This creates an output that looks like: 

{"orderID":12345,"shopperName":"John Smith","shopperEmail":"johnsmith@example.com",
"contents":[{"productID":34,"productName":"SuperWidget","quantity":1},
{"productID":56,"productName":"WonderWidget","quantity":3}],
"orderCompleted":true}

The output is a little more difficult to read simply because it's compact for sending.

##Creating JavaScript variables from JSON strings
JSON.parse() is a method that JavaScript has take the JSON string and make a JavaScript object with the JSON data.
In the following code, a variable jsonString has been created holding the JSON string. 
Passing the string through JSON.parse() creates an object holding the JSON data. 

    <script type="text/javascript">
 
        var jsonString = 
        {
          "orderID": 12345,
          "shopperName": "John Smith",
          "shopperEmail": "johnsmith@example.com",
          "contents": [
            {
              "productID": 34,
              "productName": "SuperWidget",
              "quantity": 1
            },
            {
              "productID": 56,
              "productName": "WonderWidget",
              "quantity": 3
            }
          ],
          "orderCompleted": true
        };

        var cart = JSON.parse ( jsonString);
 
        alert ( cart.shopperEmail ); 
        alert ( cart.contents[1].productName);

        </script>


To make sure the conversion worked, the shopperEmail property and the productName in the contents array is displayed:

    johnsmith@example.com
    WonderWidget



This example was courtesy of: http://www.elated.com/articles/json-basics.

# What is GSON?
Gson was originally created for use inside Google.

Gson is a Java library used to convert Java Objects into their JSON representation or JSON strings to the equivalent Java Object. 

You don’t even need the source code for the objects.

There are 2 Methods used to convert Java Objects to/from JSON.
* toJson() converts Java objects to JSON strings
* fromJson() converts JSON to Java objects

You can get the GSON library at: https://code.google.com/p/google-gson/

This is also a good site for more information.


# Helpful Websites
JSON
* http://www.elated.com/articles/json-basics/
* http://www.copterlabs.com/blog/json-what-it-is-how-it-works-how-to-use-it/
* http://www.tutorialspoint.com/json/json_tutorial.pdf
* http://www.yourinspirationweb.com/en/do-you-want-to-use-json-but-dont-know-where-to-start/

GSON
* https://code.google.com/p/google-gson/
* http://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/


