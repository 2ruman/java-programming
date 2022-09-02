#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/demo/cmd-runner/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/demo/cmd-runner/bin -sourcepath /truman/github-2ruman/java-programming/demo/cmd-runner/src /truman/github-2ruman/java-programming/demo/cmd-runner/src/truman/java/demo/cmd_runner/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/demo/cmd-runner/bin truman.java.demo.cmd_runner.Main

