#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/shutdown-hook/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/shutdown-hook/bin -sourcepath /truman/github-2ruman/java-programming/example/shutdown-hook/src /truman/github-2ruman/java-programming/example/shutdown-hook/src/truman/java/example/shutdown_hook/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/shutdown-hook/bin truman.java.example.shutdown_hook.Main

