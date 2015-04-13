import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class byteRequestThread extends clientHelperThread
{
	private Scanner serverInput;
	private PrintWriter serverOutput;
	private Socket theSocket;

	public byteRequestThread(int[] theFileArray, Socket theSocket)
	{
		super(theFileArray, theSocket);
		this.theSocket = theSocket;
		System.out.println(theSocket);
		
		try
		{
			
			//ability to read from server
			this.serverInput = new Scanner(this.theSocket.getInputStream());
			
			//ability to write to the server
			this.serverOutput = new PrintWriter(this.theSocket.getOutputStream(),true);
			
		}
		catch(Exception e)
		{
			//take care of exceptions here
			e.printStackTrace();
		}
	}

	public void run()
	{
		
		//this guy constantly listens for a request from the server
		//for a certain byte and responds with that byte
		while(true)
		{
			System.out.println("LOOP ENTERED");
			//*****Write Code HERE****

			String request = this.serverInput.nextLine(); //receive request
			System.out.println("Received request");
			serverOutput.println(theFileArray[Integer.parseInt(request)]); //return the byte requested
			System.out.println("Fulfilled Request");
		}



	}


}
