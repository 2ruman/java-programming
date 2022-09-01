#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/run-cmd-in-sub-proc/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/run-cmd-in-sub-proc/bin -sourcepath /truman/github-2ruman/java-programming/example/run-cmd-in-sub-proc/src /truman/github-2ruman/java-programming/example/run-cmd-in-sub-proc/src/truman/java/example/run_cmd_in_sub_proc/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/run-cmd-in-sub-proc/bin truman.java.example.run_cmd_in_sub_proc.Main

