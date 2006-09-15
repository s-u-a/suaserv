package de.cdauth.sua.suaserv;

public class User extends JDBCDataset
{
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
	
	public static String resolveName(String a_name)
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
