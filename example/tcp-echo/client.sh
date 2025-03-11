#!/bin/bash

#Clean up all the class files
rm -rf bin/client

shopt -s globstar

#Compile the main java with all dependencies
javac -d bin/client common/src/main/java/**/*.java client/src/main/java/**/*.java

#Run the compiled main class
java -cp bin/client truman.java.example.tcp_echo.client.Main
