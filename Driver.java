import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class Driver 
{
	static LinkedList<String> messages = new LinkedList<String>(); //global list of received messages for all threads
	static boolean modifying = false;
	
	public static void main(String[] args) throws Exception
	{
		//******Client Code******
		//connect to the server
		Socket theServer = new Socket("localhost",1234);
		//messages.add("Begin");
		ClientCommunicationProtocol ccp = new ClientCommunicationProtocol(theServer);
		ccp.startCommunication();
	}
}
