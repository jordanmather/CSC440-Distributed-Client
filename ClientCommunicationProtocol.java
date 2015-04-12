import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientCommunicationProtocol 
{
	private Socket theServer;
	private Scanner serverInput;
	private Scanner terminalInput;
	private PrintWriter serverOutput;
	private int[] theFileBytes;
	private fileFillThread fft;
	private byteRequestThread brt;
	
	public ClientCommunicationProtocol(Socket theServer) throws Exception
	{
		this.theServer = theServer;
		
		//ability to read from server
		this.serverInput = new Scanner(this.theServer.getInputStream());

		//ability to read from local client
		this.terminalInput = new Scanner(System.in);

		//ability to write to the server
		this.serverOutput = new PrintWriter(this.theServer.getOutputStream(),true);
	}
	
	private void shareFile() throws Exception
	{
		this.serverOutput.println("share");
		File myFilesDir = new File("./myFiles");
		String[] theFiles = myFilesDir.list();
		int pos = 0;
		for(String fn : theFiles)
		{
			System.out.println(pos + ": " + fn);
			pos++;
		}
		System.out.print("Which file would you like to share?");
		String theAnswer = terminalInput.nextLine();
		System.out.println("You chose to share: " + theFiles[Integer.parseInt(theAnswer)]);
		
		//Read the file from the client
		File theFile = new File("./myFiles/" + theFiles[Integer.parseInt(theAnswer)]);
		FileInputStream fis = new FileInputStream(theFile);

		//let the server know about the file we are about to send
		serverOutput.println(theFiles[Integer.parseInt(theAnswer)]);
		serverOutput.println(fis.available());
		
		theFileBytes = new int[fis.available()];
		pos = 0;
		while(fis.available() > 0)
		{
			theFileBytes[pos] = fis.read();
			pos++;
		}
		System.out.println("Locally Buffered File");
		
		//we only need to spawn a byteRequestThread since we have
		//all of the bytes already
		brt = new byteRequestThread(this.theFileBytes);
		brt.start();
	}
	
	private void getFile()
	{
		this.serverOutput.println("get");
		String theFileName = this.serverInput.nextLine();
		int theFileSize = Integer.parseInt(this.serverInput.nextLine());
		theFileBytes = new int[theFileSize];
		java.util.Arrays.fill(theFileBytes, -1);
		System.out.println("Ready to receive: " + theFileName + "(" + theFileSize +  " bytes)");
		//we need to spawn both a byteRequest and a fileFill Thread
		//since we are participating in sharing the portion of the
		//file we have, and we want to fill in the rest of the file
		//we do not have
		brt = new byteRequestThread(this.theFileBytes);
		brt.start();
		fft = new fileFillThread(this.theFileBytes);
		fft.start();
	}
	
	public void startCommunication() throws Exception
	{
		System.out.print("What do you want to do? (share or get):");
		String answer = terminalInput.nextLine();
		
		if(answer.equalsIgnoreCase("share"))
		{
			//do share stuff
			this.shareFile();
		}
		else
		{
			//do get stuff
			this.getFile();
		}
	}
}
