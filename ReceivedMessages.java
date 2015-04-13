import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class ReceivedMessages extends Thread 
{
	/*
	 * The goal of this class is to receive all the messages sent to this socket and 
	 * put them in a list that the byteRequestThread and the fileFillThread can search through to
	 * grab the messages they need
	 */


	private Socket theSocket;
	private Scanner serverInput;

	public ReceivedMessages(Socket theSocket)
	{
		this.theSocket = theSocket;

		try
		{

			//ability to read from server
			this.serverInput = new Scanner(this.theSocket.getInputStream());


		}
		catch(Exception e)
		{
			//take care of exceptions here
			e.printStackTrace();
		}
	}

	public void run()
	{
		/*
		 * Tried to ensure that the messages linkedlist was free when I edited it, ended up locking the byteRequest thread
		 * out so that it couldn't find that there was a message to respond to.
		 */
		while(true)
		{
			System.out.println("ReceivedMessages started");
			boolean successful = false;
			while(!successful) //loop through until we're able to complete the action
			{
				while(!Driver.modifying)
				{
					
					System.out.println("1 Size of messagelist: " + Driver.messages.size());
					if(Driver.messages.size() != 0)
					{
						for(int i = 0; i < Driver.messages.size(); i++)
						{
							System.out.println("*****Message " + i + ": " + Driver.messages.get(i));
						}
					}
					System.out.println("ReceivedMessages waiting on a message to add");
					String message = this.serverInput.nextLine();
					Driver.modifying = true;
					Driver.messages.add(this.serverInput.nextLine());
					System.out.println("2 Size of messagelist: " + Driver.messages.size());
					Driver.modifying = false;
					successful = true;
				}
			}
		}
	}
}
