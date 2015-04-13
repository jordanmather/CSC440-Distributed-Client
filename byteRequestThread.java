import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class byteRequestThread extends clientHelperThread
{

	private PrintWriter serverOutput;
	private Socket theSocket;

	public byteRequestThread(int[] theFileArray, Socket theSocket)
	{
		super(theFileArray, theSocket);
		this.theSocket = theSocket;
		System.out.println(theSocket);

		try
		{

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
			
			int location = -1;
			boolean requestFound = false;
			while(!requestFound)
			{
				for(int i = 0; i < Driver.messages.size(); i++)
				{
					if(Driver.messages.get(i).contains("Request:"))
					{
						location = i;
						i = Driver.messages.size();
						requestFound = true;
					}
				}
				
			}
			//when we find a request
			String request = Driver.messages.get(location);
			//pull out the location needed
			request = request.substring(8);//move to after "Request:", and replace request with the string that follows
			//remove the message from the list
			boolean successful = false;
			while(!successful) //loop through until we're able to complete the action
			{
				while(!Driver.modifying)
				{
					Driver.modifying = true;
					Driver.messages.remove(location); //remove the message we just used
					Driver.modifying = false;
					successful = true;
				}
			}
			System.out.println("Received request");
			serverOutput.println("Response:" + theFileArray[Integer.parseInt(request)]); //return the byte requested
			System.out.println("Fulfilled Request with byte: " + request + " returning: " + theFileArray[Integer.parseInt(request)]);
		}



	}


}
