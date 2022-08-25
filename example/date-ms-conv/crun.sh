#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/date-ms-conv/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/date-ms-conv/bin -sourcepath /truman/github-2ruman/java-programming/example/date-ms-conv/src /truman/github-2ruman/java-programming/example/date-ms-conv/src/truman/java/example/date_ms_conv/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/date-ms-conv/bin truman.java.example.date_ms_conv.Main

