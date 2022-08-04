#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/get-cwd/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/get-cwd/bin -sourcepath /truman/github-2ruman/java-programming/example/get-cwd/src /truman/github-2ruman/java-programming/example/get-cwd/src/truman/java/example/get_cwd/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/get-cwd/bin truman.java.example.get_cwd.Main

