package de.cdauth.sua.suaserv;

import java.sql.Connection;

/**
 * This abstract class provides database access to any dataset that needs it.
 * @author Candid Dauth
 * @version 2.0.0
*/

public abstract class JDBCDataset extends Dataset
{
	protected static Connection sm_database = null;

	static
	{
		sm_database = Database.getConnection();
	}
}
