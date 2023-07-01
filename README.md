# Computer Networks Project

Authors:
* Eddie Carrizales
* Aayushi Choudhary
* Ahsan Ahmed

This application consists of a centralized Math server and two or more clients (the screenshots and log file demonstrate 3 clients). 
The server provides basic math calculation services to the client(s).

# Design and implementation of Server application:

The server creates a socket and listens on port 8888. The Server then runs on a loop. So it can keep accepting clients. If the program
is interrupted, then the server closes. A server-client thread class is created to respond to multiple clients simultaneously. This class
has a constructor that can take a client number and input socket to create an instance of a server-client thread. This class has a run 
method to process client messages independently. This class takes client requests in the format of “operation code|operation.” 
Ex. “1|user-name”, 1 is the code to enter the server, and the user-name parameter is the name of the client. Code 2 and 3 process requests
for calculations and exit server, respectively. After taking each client request and processing it, the server writes to the output stream, 
which delivers a message to the client. Then the server invokes the flush method on the output stream to clear the communication pipe. 
The server can do only four types of calculations: Addition, Subtraction, Multiplication, and Division (+,-,*, and /). The format of operation
it can process is two operands separated by an operator. It can not handle more than two operands.

# Design and implementation of Client application:

A Client creates a socket that has an IP address of 127.0.0.1 and a port number of 8888. A client has a buffered reader to read from the console.
It has an input stream to read from the pipe and an output stream to write to the pipe. Like the server-client thread, it also uses a while loop
to keep a consistent connection with a server process. It sends requests to the server thread in the same format as described above. After sending
a request, it reads the server response from the input stream. The client also flushes the output stream to clear the pipe. It displays server
messages in the console. If option 3 is chosen by the client, then the program breaks out of the loop and exits.
