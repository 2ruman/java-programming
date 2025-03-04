#!/bin/bash

#Clean up all the class files
rm -rf bin

#Compile the main java with all dependencies
javac -d bin -sourcepath src src/truman/java/util/hot_console/Main.java

#Run the compiled main class
java -cp bin truman.java.util.hot_console.Main

