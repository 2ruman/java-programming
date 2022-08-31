#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/demo/ipc-using-streams/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/demo/ipc-using-streams/bin -sourcepath /truman/github-2ruman/java-programming/demo/ipc-using-streams/src /truman/github-2ruman/java-programming/demo/ipc-using-streams/src/truman/java/demo/ipc_using_streams/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/demo/ipc-using-streams/bin truman.java.demo.ipc_using_streams.Main

