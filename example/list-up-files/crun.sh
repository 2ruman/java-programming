#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/list-up-files/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/list-up-files/bin -sourcepath /truman/github-2ruman/java-programming/example/list-up-files/src /truman/github-2ruman/java-programming/example/list-up-files/src/truman/java/example/list_up_files/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/list-up-files/bin truman.java.example.list_up_files.Main

