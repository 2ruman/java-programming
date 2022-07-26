#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/keep-input-line/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/keep-input-line/bin -sourcepath /truman/github-2ruman/java-programming/example/keep-input-line/src /truman/github-2ruman/java-programming/example/keep-input-line/src/truman/java/example/keep_input_line/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/keep-input-line/bin truman.java.example.keep_input_line.Main

