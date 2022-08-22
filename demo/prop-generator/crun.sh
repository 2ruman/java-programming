#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/demo/prop-generator/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/demo/prop-generator/bin -sourcepath /truman/github-2ruman/java-programming/demo/prop-generator/src /truman/github-2ruman/java-programming/demo/prop-generator/src/truman/java/demo/prop_generator/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/demo/prop-generator/bin truman.java.demo.prop_generator.Main

