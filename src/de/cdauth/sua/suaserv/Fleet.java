package de.cdauth.sua.suaserv;

import java.util.Date;
import java.util.Hashtable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides a dataset for fleets.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Fleet extends JDBCDataset
{
	public final static int FLEET_COLONISATION = 1;
	public final static int FLEET_GATHERING = 2;
	public final static int FLEET_ATTACK = 3;
	public final static int FLEET_TRANSPORT = 4;
	public final static int FLEET_ESPIONAGE = 5;
	public final static int FLEET_STATIONING = 6;

	/**
	 * As every class that uses Instantiation, this static method returns
	 * an instance for the specified fleet to ensure that not two instances
	 * of the same fleet exist.
	 * @param a_id The fleet id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static Fleet getInstance(String a_id)
		throws InstantiationException,SQLException
	{
		Fleet instance = (Fleet)getInstance(Fleet.class, a_id);
		if(instance == null)
		{
			instance = new Fleet(a_id);
			addInstance(Fleet.class, a_id, instance);
		}
		return instance;
	}


	/**
	 * Creates a new empty fleet in the database.
	 * @param a_id The fleet id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void create(String a_id)
		throws InstantiationException,SQLException
	{
		boolean exists = true;
		try
		{
			checkExistance(a_id);
		}
		catch(InstantiationException e)
		{
			exists = false;
		}
		if(exists)
			throw new InstantiationException("Fleet " + a_id + " does already exist.");

		PreparedStatement stmt = sm_database.prepareStatement("INSERT INTO t_fleets ( c_fleet_id ) VALUES ( ? )");
		stmt.setString(1, a_id);
		stmt.execute();
	}

	/**
	 * Removes a fleet from the database.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void destroy(String a_id)
		throws InstantiationException,SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("DELETE FROM t_fleets WHERE c_fleet_id = ?");
		stmt.setString(1, a_id);
		if(stmt.executeUpdate() < 1)
			throw new InstantiationException(Fleet.class, a_id);
	}

	/**
	 * Checks if a fleet with the given id exists.
	 * @param a_id The requested fleet id.
	 * @throws InstantiationException The fleet does not exist.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void checkExistance(String a_id)
		throws InstantiationException,SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("SELECT count(*) FROM t_fleets WHERE c_fleet_id = ?");
		stmt.setString(1, a_id);
		ResultSet res = stmt.executeQuery();
		res.next();
		switch(res.getInt(1))
		{
			case 0: throw new InstantiationException(Fleet.class, a_id);
			case 1: break;
			default: Logger.warning("There seem to be multiple database entries for fleet " + a_id); break;
		}
	}

	private Fleet(String a_id)
		throws InstantiationException,SQLException
	{
		m_id = a_id;

		checkExistance(m_id);
	}
}