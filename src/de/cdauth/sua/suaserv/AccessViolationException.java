package de.cdauth.sua.suaserv;

/**
 * This exception is thrown whenever a client tries to perform an action
 * that the current user account has no permissions to.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class AccessViolationException extends Exception
{
	public AccessViolationException()
	{
		super();
	}

	public AccessViolationException(String a_message)
	{
		super(a_message);
	}
}
