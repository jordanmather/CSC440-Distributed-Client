import java.net.Socket;


public abstract class clientHelperThread extends Thread
{
	protected int[] theFileArray;
	protected Socket theSocket;
	
	clientHelperThread(int[] theFileArray, Socket theSocket)
	{
		this.theFileArray = theFileArray;
		this.theSocket = theSocket;
	}
}
