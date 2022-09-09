#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/loop-scanner/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/loop-scanner/bin -sourcepath /truman/github-2ruman/java-programming/util/loop-scanner/src /truman/github-2ruman/java-programming/util/loop-scanner/src/truman/java/util/loop_scanner/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/loop-scanner/bin truman.java.util.loop_scanner.Main

