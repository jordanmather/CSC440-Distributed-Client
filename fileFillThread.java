import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class fileFillThread extends clientHelperThread
{
	private Scanner serverInput;
	private PrintWriter serverOutput;
	private Socket theSocket;


	public fileFillThread(int[] theFileArray, Socket theSocket)
	{
		super(theFileArray, theSocket);
		this.theSocket = theSocket;
		System.out.println(theSocket);

		try
		{
			System.out.println("Starting to fill array");
			//ability to read from server
			this.serverInput = new Scanner(this.theSocket.getInputStream());

			//ability to write to the server
			this.serverOutput = new PrintWriter(this.theSocket.getOutputStream(),true); 
		}
		catch(Exception e)
		{
			//take care of exceptions here
		}
	}

	public void run()
	{
		//this guy constantly tries to fill the byte array for the
		//receiving file
		//*****Write Code HERE****


		System.out.println("Looking for spots to fill");
		for(int i = 0; i <= theFileArray.length; i++)
		{
			if(theFileArray[i] == -1)
			{
				System.out.println("Spot found: commence requesting byte at spot: " + i);
				//****get the byte*****
				serverOutput.println(i);//ask server to send byte
				theFileArray[i] = Integer.parseInt(serverInput.nextLine()); //receive byte and enter in array
			}
		}
		//once we get to the end of the array, write to disk
		byte[] array = new byte[theFileArray.length];//convert int[] to byte[] so fos.write works
		for(int i = 0; i < theFileArray.length; i++)
		{
			array[i] = (byte) theFileArray[i];
		}
		try
		{
			FileOutputStream fos = new FileOutputStream("\\bin\\myFiles\\newFile.png");
			fos.write(array);
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


	}
}
