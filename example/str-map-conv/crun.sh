#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/str-map-conv/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/str-map-conv/bin -sourcepath /truman/github-2ruman/java-programming/example/str-map-conv/src /truman/github-2ruman/java-programming/example/str-map-conv/src/truman/java/example/str_map_conv/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/str-map-conv/bin truman.java.example.str_map_conv.Main

