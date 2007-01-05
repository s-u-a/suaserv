package de.cdauth.sua.suaserv;

/**
 * Instances of this class represent coordinates in the universe. Each position
 * consists of three coordinates.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Coordinates
{
	private short m_galaxy;
	private short m_system;
	private short m_planet;

	public Coordinates(short a_galaxy, short a_system, short a_planet)
	{
		m_galaxy = a_galaxy;
		m_system = a_system;
		m_planet = a_planet;
	}

	public short getGalaxy()
	{
		return m_galaxy;
	}

	public short getSystem()
	{
		return m_system;
	}

	public short getPlanet()
	{
		return m_planet;
	}
}