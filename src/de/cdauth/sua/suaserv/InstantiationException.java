package de.cdauth.sua.suaserv;

/**
 * This exception is thrown whenever an Instantiation object is tried
 * to be accessed that does not exist.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class InstantiationException extends Exception
{
	public InstantiationException(String a_message)
	{
		super(a_message);
	}

	public InstantiationException(Class a_class, String a_id)
	{
		super("Instantiation object of " + a_class + " does not exist: " + a_id);
	}
}