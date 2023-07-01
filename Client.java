import java.net.*;
import java.io.*;

public class Client {	// Client class
	public static void main(String[] args) throws Exception {	// Main method
		  try{
		    Socket socket=new Socket("127.0.0.1",8888);	// Socket to connect to server
		    DataInputStream inStream=new DataInputStream(socket.getInputStream());	// Input stream to read from server
		    DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());	// Output stream to write to server

		    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));	// Buffer reader to read from console
		    String clientMessage="";	// String to store client message
		    String serverMessage="";	// String to store server message
		    
		    boolean inServer = false;
		    while(true){
			    System.out.println("\n--- OPTIONS --- \n1. Enter the server \n2. Enter a Calculation \n3. Exit the server");	// Print options
			    System.out.print("Pick an option: ");	// Prompt user to pick an option
			   	clientMessage=br.readLine();	// Read user input

			   	if(clientMessage.equals("1")) {	// If user picks option 1
					inServer = true;
			   		System.out.print("Enter your name to get started: ");	// Prompt user to enter name
				   	clientMessage=br.readLine();	// Read user input
				   	clientMessage = "1|" + clientMessage;	// Add option 1 to user input
				    outStream.writeUTF(clientMessage);	// Send message to server
				    outStream.flush();	// Flush output stream
				    serverMessage=inStream.readUTF();	// Read server message
				    System.out.println(serverMessage);	// Print server message
			   	} else if (clientMessage.equals("2") && inServer) {
					System.out.print("Enter calculation input: ");	// Prompt user to enter calculation
					clientMessage=br.readLine();	// Read user input
					clientMessage = "2|" + clientMessage;	// Add option 2 to user input
					outStream.writeUTF(clientMessage);	// Send message to server
					outStream.flush();	// Flush output stream
					serverMessage=inStream.readUTF();	// Read server message
					System.out.println(serverMessage);	// Print server message
				} else if (clientMessage.equals("2") && !inServer) {
					System.out.println("Client must first enter the server.");
				} else if (clientMessage.equals("3") && inServer) {
					break;
				} else if (clientMessage.equals("3") && !inServer) {
					System.out.println("Client must first enter the server.");
				} else
					System.out.println("Input option not available, please enter an option from above.");
			} //end of while loop
		    
		    outStream.close();	// Close output stream
		    outStream.close();	// Close input stream
		    socket.close();	// Close socket
		  }catch(Exception e){	// Catch any exceptions
		    System.out.println(e);
		  }
	}
}