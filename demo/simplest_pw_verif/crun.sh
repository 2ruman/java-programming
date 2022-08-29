#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/demo/simplest_pw_verif/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/demo/simplest_pw_verif/bin -sourcepath /truman/github-2ruman/java-programming/demo/simplest_pw_verif/src /truman/github-2ruman/java-programming/demo/simplest_pw_verif/src/truman/java/demo/simplest_pw_verif/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/demo/simplest_pw_verif/bin truman.java.demo.simplest_pw_verif.Main

