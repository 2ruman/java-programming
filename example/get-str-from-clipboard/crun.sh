#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/get-str-from-clipboard/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/get-str-from-clipboard/bin -sourcepath /truman/github-2ruman/java-programming/example/get-str-from-clipboard/src /truman/github-2ruman/java-programming/example/get-str-from-clipboard/src/truman/java/example/get_str_from_clipboard/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/get-str-from-clipboard/bin truman.java.example.get_str_from_clipboard.Main

