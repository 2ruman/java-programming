#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/abs-selection/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/abs-selection/bin -sourcepath /truman/github-2ruman/java-programming/util/abs-selection/src /truman/github-2ruman/java-programming/util/abs-selection/src/truman/java/util/abs_selection/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/abs-selection/bin truman.java.util.abs_selection.Main

