# TCP Echo Client/Server
This project has three sub-modules, each organized into its own directory.
Each of the modules has respective role to play complete communication between echoing server and client.

## Dev Environment
  - OS : _Ubuntu 24.04.2 LTS_  
  - IDE : _Intellij_  
  - JDK : _OpenJDK 17.0.14_  

<br>

## Directory Tree
  - __client__ : Implementation of client who sends and gets back the message from the server  
  - __server__ : Implementation of server who receives and echo back the message  to the client  
  - __common__ : Common implementation which is shared by client and server  

<br>

## Note
Without giving proper arguments to the main, the client is suppposed to connect to _localhost_, so the network interface will be _loopback_.
As the client and server inter-communicate without a security layer, you can dump the plain packet data using some commad like:  
```
$ sudo tcpdump -i lo -A | grep [KEYWORD_TO_CAPTURE]
```
