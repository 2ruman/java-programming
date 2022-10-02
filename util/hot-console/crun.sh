#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/hot-console/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/hot-console/bin -sourcepath /truman/github-2ruman/java-programming/util/hot-console/src /truman/github-2ruman/java-programming/util/hot-console/src/truman/java/util/hot_console/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/hot-console/bin truman.java.util.hot_console.Main

