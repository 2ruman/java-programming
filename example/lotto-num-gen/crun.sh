#!/bin/bash

#Clean up all the class files
rm -rf /truman/github-2ruman/java-programming/example/lotto-num-gen/bin

#Compile the main java with all dependencies
javac -d /truman/github-2ruman/java-programming/example/lotto-num-gen/bin -sourcepath /truman/github-2ruman/java-programming/example/lotto-num-gen/src /truman/github-2ruman/java-programming/example/lotto-num-gen/src/truman/java/example/lotto_num_gen/Main.java

#Run the compiled main class
java -cp /truman/github-2ruman/java-programming/example/lotto-num-gen/bin truman.java.example.lotto_num_gen.Main

