package de.cdauth.sua.suaserv;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class System extends JDBCDataset
{
	private short m_galaxy;
	private short m_system;

	public static System getInstance(short a_galaxy, short a_system)
		throws InstantiationException,SQLException
	{
		System instance = (System)getInstance(System.class, makeID(a_galaxy, a_system));
		if(instance == null)
		{
			instance = new System(a_galaxy, a_system);
			addInstance(System.class, instance);
		}
		return instance;
	}

	public static void checkExistance(short a_galaxy, short a_system)
		throws InstantiationException,SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("SELECT count(*) FROM t_planets WHERE c_galaxy = ? AND c_system = ?");
		stmt.setShort(1, a_galaxy);
		stmt.setShort(2, a_system);
		ResultSet res = stmt.executeQuery();
		res.next();
		if(res.getInt(1) < 1)
			throw new InstantiationException(System.class, makeID(a_galaxy, a_system));
	}

	public System(short a_galaxy, short a_system)
		throws InstantiationException,SQLException
	{
		m_id = makeID(a_galaxy, a_system);
		m_galaxy = a_galaxy;
		m_system = a_system;

		checkExistance(m_galaxy, m_system);
	}

	public short getPlanetCount()
		throws SQLException
	{
		short i = 0;
		PreparedStatement stmt = sm_database.prepareStatement("SELECT DISTINCT c_system FROM t_planets WHERE c_galaxy = ? AND c_system = ?");
		stmt.setShort(1, m_galaxy);
		stmt.setShort(1, m_system);
		ResultSet res = stmt.executeQuery();
		while(res.next()) i++;
		return i;
	}

	public static String makeID(short a_galaxy, short a_system)
	{
		return Short.toString(a_galaxy) + " " + Short.toString(a_system);
	}
}