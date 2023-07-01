import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerClientThread extends Thread {
	
	  Socket serverClient;
	  int clientNo; 	// to keep track of client number
	  Date d1 = new Date();	// to get current date and time
      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");	// to format date and time
      String formattedDate = df.format(d1);


      String userInfo[] = new String[100];	// to store user info
      
	  ServerClientThread(Socket inSocket,int counter){	// constructor
	    serverClient = inSocket;
	    clientNo=counter;
	  }
	  
	  public void run() {

		  BufferedWriter log = null;	// to write to log file
		  try {
			  DataInputStream inStream = new DataInputStream(serverClient.getInputStream());	// to read from client
			  DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());	// to write to client

			  String clientMessage = "";	// to store client message
			  String serverMessage = "";	// to store server message
			  double result = 0.0;	// to initialize result of calculation

			  while (true) {	// loop to keep server running
				  clientMessage = inStream.readUTF();	// read client message
				  String param1 = clientMessage.substring(0, clientMessage.indexOf("|"));	// get first parameter
				  String param2 = clientMessage.substring(clientMessage.indexOf("|") + 1, clientMessage.length());	// get second parameter

				  // to create and write to a file
				  FileWriter writer = new FileWriter("log.txt", true);
				  log = new BufferedWriter(writer);

				  if (param1.equals("1")) {	// if first parameter is 1

					  int counter = 0;	// to keep track of number of users
					  boolean hasUser = false;	// to check if user exists
					  while (counter < userInfo.length) {
						  if (userInfo[counter] != null) {	// if user exists
							  if (userInfo[counter].equals(param2)) {
								  hasUser = true;
							  }
						  }
						  counter += 1;
					  }

					  if (hasUser == false) {	// if user does not exist
						  System.out.println(param2 + " joined server at " + formattedDate);	// print to console
						  serverMessage = "Server join request accepted";	// set server message
						  userInfo[clientNo] = param2;	// add user to array
						  outStream.writeUTF(serverMessage);	// send server message to client
						  outStream.flush();	// flush output stream
						  log.write("Server join request accepted");	// write to log file
						  log.newLine(); //creates a new line
						  log.newLine();
						  log.flush();
						  log.write("Client #" + clientNo + " - " + userInfo[clientNo] + " joined server at " + formattedDate); //writes the name of the client connected
						  log.newLine(); //creates a new line
						  log.flush();

					  } else {	// if user exists
						  System.out.println("User with name " + param2 + " already exist");	// print to console
						  serverMessage = "Server join request failed";	// set server message
						  outStream.writeUTF(serverMessage);	// send server message to client
						  outStream.flush();
						  log.write("Server join request failed");	// write to log file
						  log.newLine(); //creates a new line
						  log.flush();
						  log.write("User with name " + param2 + " already exists");
						  log.newLine(); //creates a new line
						  log.flush();
					  }

				  } else if (param1.equals("2")) {	// if first parameter is 2
					  if (param2.contains("+")) {	// if second parameter contains +
						  String[] splitString = param2.split("\\+");	// split string

						  result = Integer.parseInt(splitString[0]) + Integer.parseInt(splitString[1]);	// add numbers
						  System.out.println("Question: " + param2 + " asked by " + userInfo[clientNo]);	// print to console
						  System.out.println("Answer: " + result);	// print to console
						  outStream.writeUTF(param2 + " = " + String.valueOf(result));	// send server message to client
						  outStream.flush();	// flush output stream
						  log.write("Question: " + param2 + " asked by " + userInfo[clientNo]);	// write to log file
						  log.newLine(); //creates a new line
						  log.flush();
						  log.write("Answer: " + result);
						  log.newLine(); //creates a new line
						  log.flush();

					  } else if (param2.contains("-")) {	// if second parameter contains -
						  String[] splitString = param2.split("-");	// split string

						  result = Integer.parseInt(splitString[0]) - Integer.parseInt(splitString[1]);	// subtract numbers
						  System.out.println("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  System.out.println("Answer: " + result);
						  outStream.writeUTF(param2 + " = " + String.valueOf(result));
						  outStream.flush();
						  log.write("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  log.newLine(); //creates a new line
						  log.flush();
						  log.write("Answer: " + result);
						  log.newLine(); //creates a new line
						  log.flush();

					  } else if (param2.contains("/")) {	// if second parameter contains /
						  String[] splitString = param2.split("/");	// split string

						  result = Double.parseDouble(splitString[0]) / Double.parseDouble(splitString[1]);	// divide numbers
						  String formatResult = String.format("%.02f", result);
						  System.out.println("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  System.out.println("Answer: " + formatResult);
						  outStream.writeUTF(param2 + " = " + formatResult);
						  outStream.flush();
						  log.write("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  log.newLine(); //creates a new line
						  log.flush();
						  log.write("Answer: " + formatResult);
						  log.newLine(); //creates a new line
						  log.flush();

					  } else if (param2.contains("*")) {	// if second parameter contains *
						  String[] splitString = param2.split("\\*");	// split string

						  result = Integer.parseInt(splitString[0]) * Integer.parseInt(splitString[1]);	// multiply numbers
						  System.out.println("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  System.out.println("Answer: " + result);
						  outStream.writeUTF(param2 + " = " + String.valueOf(result));
						  outStream.flush();
						  log.write("Question: " + param2 + " asked by " + userInfo[clientNo]);
						  log.newLine(); //creates a new line
						  log.flush();
						  log.write("Answer: " + result);
						  log.newLine(); //creates a new line
						  log.flush();

					  } else {	// if second parameter is not a valid question
						  System.out.println("Invalid operation");	// print to console
						  log.write("Invalid operation");
						  log.newLine(); //creates a new line
						  log.flush();
					  }

				  } else if (param1.equals("3")) {	// if first parameter is 3
					  break;	// break out of while loop
				  }
			  }

			  inStream.close();	// close input stream
			  outStream.close();	// close output stream
			  serverClient.close();	// close socket


		  } catch (Exception ex) {	// catch any exceptions

		  } finally {
			  Date d2 = new Date();	// get current date
			  String exitFormattedDate = df.format(d2);	// format date
			  System.out.println(userInfo[clientNo] + " left server at " + exitFormattedDate + " and was connected for " + (d2.getTime() - d1.getTime()) + " milliseconds");	// print to console
			  try{
				  log.write(userInfo[clientNo] + " left server at " + exitFormattedDate + " and was connected for " + (d2.getTime() - d1.getTime()) + " milliseconds");	// write to log file
				  log.newLine(); //creates a new line
				  log.flush();
				  log.close();
			  } catch (IOException e) {
				  e.printStackTrace();	// print stack trace
			  }
		  }
	  }
}
