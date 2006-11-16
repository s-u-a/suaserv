package de.cdauth.sua.suaserv;

/**
 * This exception is thrown whenever any user account is tried to access
 * that does not exist.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class UserException extends Exception
{
	public UserException()
	{
		super();
	}

	public UserException(String a_message)
	{
		super(a_message);
	}
}
