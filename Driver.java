import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Driver 
{
	public static void main(String[] args) throws Exception
	{
		//******Client Code******
		//connect to the server
		Socket theServer = new Socket("localhost",1234);
		ClientCommunicationProtocol ccp = new ClientCommunicationProtocol(theServer);
		ccp.startCommunication();
	}
}
