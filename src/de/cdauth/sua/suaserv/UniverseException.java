package de.cdauth.sua.suaserv;

/**
 * This exception is thrown whenever any resource in the universe is accessed
 * that does not exist.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class UniverseException extends Exception
{
	public UniverseException()
	{
		super();
	}

	public UniverseException(String a_message)
	{
		super(a_message);
	}
}
