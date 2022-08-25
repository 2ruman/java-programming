#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/ser-obj-bytes-conv/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/ser-obj-bytes-conv/bin -sourcepath /truman/github-2ruman/java-programming/example/ser-obj-bytes-conv/src /truman/github-2ruman/java-programming/example/ser-obj-bytes-conv/src/truman/java/example/ser_obj_bytes_conv/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/ser-obj-bytes-conv/bin truman.java.example.ser_obj_bytes_conv.Main

