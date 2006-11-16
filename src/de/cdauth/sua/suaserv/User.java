package de.cdauth.sua.suaserv;

/**
 * This class defines a dataset for a user account.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class User extends JDBCDataset
{
	/**
	 * As every class that uses Instantiation, this static method returns
	 * an instance for the specified user to ensure that not two instances
	 * of the same user exist.
	 * @param a_username The user's account id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static User getInstance(String a_username)
	{
		User instance = (User)getInstance(User.class, a_username);
		if(instance == null)
		{
			instance = new User(a_username);
			addInstance(User.class, a_username, new User(a_username));
		}
		return instance;
	}

	/**
	 * Resolves a player name to a user account id.
	 * @param a_name The player name to resolve.
	 * @return The user account id with the specified account id.
	 * @throws UserException The specified player name does not exist.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static String resolveName(String a_name)
		throws UserException
	{
		return "test";
	}

	public boolean checkPassword(String a_password)
	{
		return true;
	}

	private User(String username)
	{
		m_id = username;
	}
}
