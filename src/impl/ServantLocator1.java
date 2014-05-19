package impl;

import Ice.Current;
import Ice.LocalObjectHolder;
import Ice.Object;
import Ice.UserException;



public class ServantLocator1 implements Ice.ServantLocator
{
	private String id;
	
	public ServantLocator1(String id)
	{
		this.id = id;
		System.out.println("## ServantLocator1(" + id + ") ##");
	}

	public ServantLocator1(String id, Ice.Object obj)
	{
		this.id = id;
		System.out.println("## ServantLocator1(" + id + ", obj) ##");
	}

	public Object locate(Current curr, LocalObjectHolder cookie) throws UserException 
	{
		System.out.println("## ServantLocator1 #" +id + " .locate() ##");

		//TODO
		
		return null;
	}

	public void finished(Ice.Current curr, Ice.Object servant, java.lang.Object cookie) throws UserException 
	{
		System.out.println("## ServantLocator1 #" +id + " .finished() ##");
	}

	public void deactivate(String category)
	{
		System.out.println("## ServantLocator1 #" +id + " .deactivate() ##");
	}
}
