# IntelliJ IDEA Tutorial

## What is IntelliJ?
IntelliJ is an IDE (Integrated Development Environment) developed by JetBrains mainly to aid in the development of Java applications but can also handle applications using other coding languages. IntelliJ was first released in 2001 and features a Community version and an Ultimate version. The Community version can be downloaded and used free of charge while the Ultimate version, which includes more features, must be paid for.

More on IntelliJ version differences:
[https://www.jetbrains.com/idea/features/editions_comparison_matrix.html](https://www.jetbrains.com/idea/features/editions_comparison_matrix.html)

## How to get IntelliJ
To begin using IntelliJ follow the steps given below:
* Go to [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Click the Download Community (or Download Ultimate) button
* Go to your Downloads directory and run the executable file that was downloaded (File should be ideaIC-XX.X.X, where the Xs represent the version)
* Follow the steps given by the installation wizard

## IntelliJ basics
To begin coding in IntelliJ you first need to either create a new project or open an existing project.

### Creating a new project
To create a new project go to File > New Project and select the SDK and type of project you will be creating. On the next page select whether or not you would like to use a template for this project and if so, which template you would like to use. The last page asks for a project name, which can be anything, where to project will be saved, and a package name, which is used to group your files together. Click finish you create your project.
After creating a project the Project window containing your project will be open on the left while the text editor will be open on the right. 

Setting up the SDK:
If it is your first project you must manually find the SDK you will be using. If you are making a Java project click the New button next to the SDK drop-down and navigate to the newest version of the JDK that you have installed. This will typically be located in C:\Program Files\Java.

### Opening a project
To open a project go to File > Open and browse to the project you would like to open in IntelliJ.
Projects from other IDEs such as NetBeans or Eclipse can be opened using File > Import.

### Running a program in IntelliJ
Once the project has been created or opened the files in the project may be edited using the text editor. To find the files in the project first open the Project window, if it is not already open, by pressing Alt + 1 or clicking on the tab in the top left. Then under ProjectName > src > PackageName the files should be present and may be opened in the text editor by double clicking on them.

To run a project with only one main function simply go to Run > Run ‘ClassName’ or click the green arrow on the toolbar. If there is more than one main function in your project files go to Run > Run... to select the class whose main function you would like to run.
 
To pass arguments into a program go to Run > Edit Configurations and add your arguments to the Program Arguments text box. These arguments must be individual set for each main function in the project.

## Debugging in IntelliJ
Debugging is a very useful and powerful tool that allows you to see exactly what your program is doing and is an excellent way to fix errors.

Running a program in debug mode is very similar to running it normally. Instead of the green arrow the symbol for debug mode is the green bug. Debug configurations are set similarly to Run configurations.

If there are no breakpoints set in your code the program will execute as if it were being run normally, with more information being displayed in the Debug window. If a breakpoint is present, however, program will stop at the line that contains the breakpoint. To set a breakpoint either click the area directly to the left of the text editor, by clicking the line and going to Run > Toggle Line Breakpoint, or by pressing Ctrl + F8.

When running a program in debug mode the debug window will be displayed. In this window the values of local variables will be displayed in the Variables tab, and variables can be specifically watched. To put a variable in the watch tab just right click on the variable and select Add to Watches. If a variable is being watched its value will always be displayed.

When the program is stopped at a breakpoint there are buttons in the debug window that allow you to step through your program line by line to see exactly what is happening. A few of the most commonly used buttons, which can be found under Run or on the top of the debug window, with their functions are described below:

* Step Over: This will execute the current line and continue to the next line
* Step Into: This will, if possible, open the method on the current line and will continue to execute the lines in that method
* Step Out: This will, if possible, move out of the current method 

Additional information on debugging:
[https://www.jetbrains.com/idea/help/debugging.html]( https://www.jetbrains.com/idea/help/debugging.html)

## Helpful Tips
* If you accidently close the Project window with all your files press Alt + 1 to open it up again, or click on the project window which is located on the upper-left side of the IDE directly under File.
* If you would like a different color scheme go to File > Settings > Appearance & Behavior > Appearance and choose your desired theme from the Theme drop down menu.
* To comment out a block of code use press Ctrl + / for // comments or Ctrl + Shift + / for /* */ comments
* To show the member variables and methods of a class in the projects window click the image of the gear on the upper edge of the projects window and select Show Members
* To look up or change keyboard shortcuts go to File > Settings > Appearance & Behavior > Keymap
* Press Shift twice quickly to bring up a search bar, this search bar lets you search through your entire project and will navigate to the selected item
* Code Completion usually comes up when you start typing a recognized name, if it does not you can press Ctrl + Space to bring it up
* Press Tab to select an item from the Code Completion drop down menu

## Help with IntelliJ

IntelliJ IDEA Documentaion:
[https://www.jetbrains.com/idea/documentation/](https://www.jetbrains.com/idea/documentation/)

Video Tutorials:
[https://www.jetbrains.com/idea/documentation/video-tutorials.jsp]( https://www.jetbrains.com/idea/documentation/video-tutorials.jsp)