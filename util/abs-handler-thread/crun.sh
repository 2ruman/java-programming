#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/abs-handler-thread/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/abs-handler-thread/bin -sourcepath /truman/github-2ruman/java-programming/util/abs-handler-thread/src /truman/github-2ruman/java-programming/util/abs-handler-thread/src/truman/java/util/abs_handler_thread/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/abs-handler-thread/bin truman.java.util.abs_handler_thread.Main

