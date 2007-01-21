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
	 * @param a_id The user's account id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static User getInstance(String a_id)
		throws InstantiationException
	{
		User instance = (User)getInstance(User.class, a_id);
		if(instance == null)
		{
			instance = new User(a_id);
			addInstance(User.class, instance);
		}
		return instance;
	}

	private User(String a_id)
		throws InstantiationException
	{
		m_id = a_id;
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
		throws InstantiationException
	{
		return "test";
	}

	public boolean checkPassword(String a_password)
	{
		return true;
	}
}
