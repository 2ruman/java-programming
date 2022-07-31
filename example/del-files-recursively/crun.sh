#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/del-files-recursively/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/del-files-recursively/bin -sourcepath /truman/github-2ruman/java-programming/example/del-files-recursively/src /truman/github-2ruman/java-programming/example/del-files-recursively/src/truman/java/example/del_files_recursively/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/del-files-recursively/bin truman.java.example.del_files_recursively.Main

