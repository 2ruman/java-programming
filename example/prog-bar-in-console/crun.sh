#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/prog-bar-in-console/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/prog-bar-in-console/bin -sourcepath /truman/github-2ruman/java-programming/example/prog-bar-in-console/src /truman/github-2ruman/java-programming/example/prog-bar-in-console/src/truman/java/example/prog_bar_in_console/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/prog-bar-in-console/bin truman.java.example.prog_bar_in_console.Main

