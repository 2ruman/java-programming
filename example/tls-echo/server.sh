#!/bin/bash

#Clean up all the class files
rm -rf bin/server

shopt -s globstar

#Compile the main java with all dependencies
javac -d bin/server common/src/main/java/**/*.java server/src/main/java/**/*.java

#Run the compiled main class
java -cp bin/server truman.java.example.tls_echo.server.Main
