package de.cdauth.sua.suaserv;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Planet extends JDBCDataset
{
	private short m_galaxy;
	private short m_system;
	private short m_planet;

	public static Planet getInstance(short a_galaxy, short a_system, short a_planet)
		throws InstantiationException,SQLException
	{
		Planet instance = (Planet)getInstance(Planet.class, makeID(a_galaxy, a_system, a_planet));
		if(instance == null)
		{
			instance = new Planet(a_galaxy, a_system, a_planet);
			addInstance(Planet.class, instance);
		}
		return instance;
	}

	public static void checkExistance(short a_galaxy, short a_system, short a_planet)
		throws InstantiationException,SQLException
	{
		PreparedStatement stmt = sm_database.prepareStatement("SELECT count(*) FROM t_planets WHERE c_galaxy = ? AND c_system = ? AND c_planet = ?");
		stmt.setShort(1, a_galaxy);
		stmt.setShort(2, a_system);
		stmt.setShort(3, a_planet);
		ResultSet res = stmt.executeQuery();
		res.next();
		if(res.getInt(1) < 1)
			throw new InstantiationException(Planet.class, makeID(a_galaxy, a_system, a_planet));
	}

	public Planet(short a_galaxy, short a_system, short a_planet)
		throws InstantiationException,SQLException
	{
		m_id = makeID(a_galaxy, a_system, a_planet);
		m_galaxy = a_galaxy;
		m_system = a_system;
		m_planet = a_planet;

		checkExistance(m_galaxy, m_system, m_planet);
	}

	public static String makeID(short a_galaxy, short a_system, short a_planet)
	{
		return Short.toString(a_galaxy) + " " + Short.toString(a_system) + " " + Short.toString(a_planet);
	}
}