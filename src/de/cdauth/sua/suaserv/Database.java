package de.cdauth.sua.suaserv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides an interface to access the database. It loads
 * the required database driver, opens the connection and provides
 * methods to access it.
 * @author Candid Dauth
 * @version 2.0.0
*/

class Database
{
	private static Connection sm_connection = null;

	/**
	 * Initiates the database connection.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void initConnection()
		throws ClassNotFoundException,SQLException
	{
		Class.forName(ConfigurationManager.getStringSetting("database_driver"));
		sm_connection = DriverManager.getConnection(ConfigurationManager.getStringSetting("database_connection"));
	}


	/**
	 * Returns an object to access the database.
	 * @return A java.sql.Connection object connected to the database.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static Connection getConnection()
	{
		return sm_connection;
	}
}