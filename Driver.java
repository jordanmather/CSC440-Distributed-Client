import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Driver 
{
	public static void main(String[] args) throws Exception
	{
		//connect to the server
		Socket s = new Socket("localhost",1234);
		
		//ability to read from server
		Scanner input = new Scanner(s.getInputStream());
		
		//ability to read from local client
		Scanner terminalInput = new Scanner(System.in);
		
		//ability to write to the server
		PrintWriter output = new PrintWriter(s.getOutputStream(),true);
		
		System.out.println(input.nextLine());
		String theAnswer = terminalInput.nextLine();
		output.println(theAnswer);

		if(theAnswer.equals("share"))
		{
			File myFilesDir = new File("src\\myFiles");
			String[] theFiles = myFilesDir.list();
			int pos = 0;
			for(String fn : theFiles)
			{
				System.out.println(pos + ": " + fn);
				pos++;
			}
			System.out.print("Which file would you like to share?");
			theAnswer = terminalInput.nextLine();
			System.out.println("You chose to share: " + theFiles[Integer.parseInt(theAnswer)]);
			
			File theFile = new File("src\\myFiles\\" + theFiles[Integer.parseInt(theAnswer)]);
			
			//***I tried these things, but they weren't working out***
			//let server know the size of the file
			//output.print(theFile.length());
			
			//let server know the name of the file
			//output.print(theFiles[Integer.parseInt(theAnswer)]);
			
			
			FileInputStream fis = new FileInputStream(theFile);
			
			//send file
			byte[] theByteArray = new byte[(int)theFile.length()];
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(theByteArray, 0, theByteArray.length);
			OutputStream os = s.getOutputStream();
			System.out.println("Sending: " + theFiles[Integer.parseInt(theAnswer)] + " (" + theByteArray.length + " bytes)");
			os.write(theByteArray, 0, theByteArray.length);
			os.flush();
			System.out.println("Done");
			//close streams
			if (fis != null) fis.close();
			if (bis != null) bis.close();
	        if (os != null) os.close();
			
		}
	}
}
