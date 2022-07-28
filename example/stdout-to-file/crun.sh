#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/stdout-to-file/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/stdout-to-file/bin -sourcepath /truman/github-2ruman/java-programming/example/stdout-to-file/src /truman/github-2ruman/java-programming/example/stdout-to-file/src/truman/java/example/stdout_to_file/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/stdout-to-file/bin truman.java.example.stdout_to_file.Main

