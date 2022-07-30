#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/search-sys-property/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/search-sys-property/bin -sourcepath /truman/github-2ruman/java-programming/example/search-sys-property/src /truman/github-2ruman/java-programming/example/search-sys-property/src/truman/java/example/search_sys_property/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/search-sys-property/bin truman.java.example.search_sys_property.Main

