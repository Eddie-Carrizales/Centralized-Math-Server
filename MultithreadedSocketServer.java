import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedSocketServer {

	public static void main(String[] args) throws Exception {
		try{
		      ServerSocket server=new ServerSocket(8888);	// Server socket
		      int counter=0;	// Counter for clients
		      System.out.println("Server is listening...");

		      while(true){	// Infinite loop
		        counter++;	// Increment counter
		        Socket serverClient=server.accept();  //server accept the client connection request
		        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
		        sct.start();	// Start the thread
		      }
		    }catch(Exception e){	// Catch any exceptions
		      System.out.println(e);
		    }
	}
}
