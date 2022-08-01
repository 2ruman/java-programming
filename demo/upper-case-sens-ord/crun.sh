#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/demo/upper-case-sens-ord/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/demo/upper-case-sens-ord/bin -sourcepath /truman/github-2ruman/java-programming/demo/upper-case-sens-ord/src /truman/github-2ruman/java-programming/demo/upper-case-sens-ord/src/truman/java/demo/upper_case_sens_ord/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/demo/upper-case-sens-ord/bin truman.java.demo.upper_case_sens_ord.Main

