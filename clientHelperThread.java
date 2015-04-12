
public abstract class clientHelperThread extends Thread
{
	protected int[] theFileArray;
	
	clientHelperThread(int[] theFileArray)
	{
		this.theFileArray = theFileArray;
	}
}
