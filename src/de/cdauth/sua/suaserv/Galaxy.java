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
	private short m_galaxy;

	/**
	 * As every class that uses Instantiation, this static method returns
	 * an instance for the specified galaxy to ensure that not two instances
	 * of the same galaxy exist.
	 * @param a_id The number of the galaxy.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static Galaxy getInstance(short a_galaxy)
		throws InstantiationException,SQLException
	{
		Galaxy instance = (Galaxy)getInstance(Galaxy.class, makeID(a_galaxy));
		if(instance == null)
		{
			instance = new Galaxy(a_galaxy);
			addInstance(Galaxy.class, instance);
		}
		return instance;
	}

	public static void checkExistance(short a_galaxy)
		throws InstantiationException,SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("SELECT count(*) FROM t_planets WHERE c_galaxy = ?");
		stmt.setShort(1, a_galaxy);
		ResultSet res = stmt.executeQuery();
		res.next();
		if(res.getInt(1) < 1)
			throw new InstantiationException(Galaxy.class, makeID(a_galaxy));
	}

	public Galaxy(short a_galaxy)
		throws InstantiationException,SQLException
	{
		m_galaxy = a_galaxy;
		m_id = makeID(a_galaxy);

		checkExistance(a_galaxy);
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
		PreparedStatement stmt = sm_database.prepareStatement("SELECT DISTINCT c_system FROM t_planets WHERE c_galaxy = ?");
		stmt.setShort(1, m_galaxy);
		ResultSet res = stmt.executeQuery();
		while(res.next()) i++;
		return i;
	}

	public static String makeID(short a_galaxy)
	{
		return Short.toString(a_galaxy);
	}
}
