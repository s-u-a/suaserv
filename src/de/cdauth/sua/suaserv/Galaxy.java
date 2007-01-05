package de.cdauth.sua.suaserv;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Every object of this class accesses one galaxy in the game.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Galaxy extends JDBCDataset
{
	/**
	 * As every class that uses Instantiation, this static method returns
	 * an instance for the specified galaxy to ensure that not two instances
	 * of the same galaxy exist.
	 * @param a_id The number of the galaxy.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static Galaxy getInstance(String a_id)
		throws InstantiationException,SQLException
	{
		Galaxy instance = (Galaxy)getInstance(Galaxy.class, a_id);
		if(instance == null)
		{
			instance = new Galaxy(a_id);
			addInstance(Galaxy.class, a_id, instance);
		}
		return instance;
	}

	public static void checkExistance(String a_id)
		throws InstantiationException,SQLException
	{
		try
		{
			PreparedStatement stmt = sm_database.prepareStatement("SELECT count(*) FROM t_planets WHERE c_galaxy = ?");
			stmt.setShort(1, Short.parseShort(a_id));
			ResultSet res = stmt.executeQuery();
			res.next();
			if(res.getInt(1) < 1)
				throw new InstantiationException(Galaxy.class, a_id);
		}
		catch(NumberFormatException e)
		{
			throw new InstantiationException(Galaxy.class, a_id);
		}
	}

	public Galaxy(String a_id)
		throws InstantiationException,SQLException
	{
		m_id = a_id;

		checkExistance(m_id);
	}

	public static short getGalaxyCount()
		throws SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("SELECT DISTINCT c_galaxy FROM t_planets");
		ResultSet res = stmt.executeQuery();
		short i = 0;
		while(res.next()) i++;
		return i;
	}

	public short getSystemCount()
		throws SQLException
	{
		short i = 0;
		try
		{
			PreparedStatement stmt = sm_database.prepareStatement("SELECT DISTINCT c_system FROM t_planets WHERE c_galaxy = ?");
			stmt.setShort(1, Short.parseShort(m_id));
			ResultSet res = stmt.executeQuery();
			while(res.next()) i++;
		}
		catch(NumberFormatException e)
		{
			Logger.warning("Invalid Galaxy DataSet id", e);
		}
		return i;
	}
}
