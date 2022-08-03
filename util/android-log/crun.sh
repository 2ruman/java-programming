#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/android-log/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/android-log/bin -sourcepath /truman/github-2ruman/java-programming/util/android-log/src /truman/github-2ruman/java-programming/util/android-log/src/truman/java/util/android_log/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/android-log/bin truman.java.util.android_log.Main

