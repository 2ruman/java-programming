#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/util/date-utils/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/util/date-utils/bin -sourcepath /truman/github-2ruman/java-programming/util/date-utils/src /truman/github-2ruman/java-programming/util/date-utils/src/truman/java/util/date_utils/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/util/date-utils/bin truman.java.util.date_utils.Main

