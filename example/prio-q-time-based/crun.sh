#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/prio-q-time-based/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/prio-q-time-based/bin -sourcepath /truman/github-2ruman/java-programming/example/prio-q-time-based/src /truman/github-2ruman/java-programming/example/prio-q-time-based/src/truman/java/example/prio_q_time_based/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/prio-q-time-based/bin truman.java.example.prio_q_time_based.Main

