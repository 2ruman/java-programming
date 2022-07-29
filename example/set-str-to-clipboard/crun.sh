#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/set-str-to-clipboard/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/set-str-to-clipboard/bin -sourcepath /truman/github-2ruman/java-programming/example/set-str-to-clipboard/src /truman/github-2ruman/java-programming/example/set-str-to-clipboard/src/truman/java/example/set_str_to_clipboard/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/set-str-to-clipboard/bin truman.java.example.set_str_to_clipboard.Main

