package de.cdauth.sua.suaserv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class Galaxy extends Dataset
{
	protected File m_file = null;
	protected FileInputStream m_in = null;
	protected FileOutputStream m_out = null;

	public static Galaxy getInstance(int a_number)
		throws IOException,UniverseException
	{
		String id = Integer.toString(a_number);
		Galaxy instance = (Galaxy)getInstance(Galaxy.class, id);
		if(instance == null)
		{
			instance = new Galaxy(a_number);
			addInstance(Galaxy.class, id, new Galaxy(a_number));
		}
		return instance;
	}

	private Galaxy(int a_number)
		throws IOException,UniverseException
	{
		m_id = Integer.toString(a_number);
		m_file = new File(Options.getDatabase()+"/universe", m_id);
		if(!m_file.exists())
			throw new UniverseException("Universe "+m_id+" does not exist.");
		m_in = new FileInputStream(m_file);
	}

	protected void ensureWritability()
		throws IOException
	{
		if(m_out == null)
			m_out = new FileOutputStream(m_file);
	}

	protected Object getCacheIndex(int a_system, int a_planet)
	{
		return Integer.toString(a_system) + ":" + Integer.toString(a_planet);
	}

	public void checkExistance(int a_system)
		throws UniverseException
	{
		if(a_system < 1 || a_system > getSystemCount())
			throw new UniverseException("System "+Integer.toString(a_system)+" does not exist in galaxy "+m_id+".");
	}

	public void checkExistance(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system);
		if(a_planet < 1 || a_planet > getPlanetCount(a_system))
			throw new UniverseException("Planet "+Integer.toString(a_planet)+" does not exist in system "+Integer.toString(a_system)+" in galaxy "+m_id+".");
	}

	public static int getGalaxyCount()
	{
		int i;
		for(i=1; true; i++)
		{
			File this_galaxy = new File(Options.getDatabase()+"/universe", Integer.toString(i));
			if(!this_galaxy.exists())
				break;
		}
		return i;
	}

	public int getSystemCount()
	{
		return (int)(m_file.length()/1655l);
	}

	public int getPlanetCount(int a_system)
		throws UniverseException
	{
		checkExistance(a_system);

		Object index = Integer.toString(a_system);
		Integer cache = (Integer)getCacheEntry(index);
		if(cache == null)
		{
			cache = new Integer(20);
			setCacheEntry(index, cache);
		}

		return cache.intValue();
	}

	public boolean isOccupied(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system, a_planet);

		Object index1 = getCacheIndex(a_system, a_planet);
		Object index2 = new Integer(0);
		Boolean cache = (Boolean)getCacheEntry(index1, index2);
		if(cache == null)
		{
			cache = new Boolean(false);
			setCacheEntry(index1, index2, cache);
		}
		return cache.booleanValue();
	}

	public synchronized void setOccupied(int a_system, int a_planet, boolean a_occupied)
		throws UniverseException,IOException
	{
		checkExistance(a_system, a_planet);

		setCacheEntry(getCacheIndex(a_system, a_planet), new Integer(0), new Boolean(a_occupied));
	}

	public String getPlanetName(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system, a_planet);

		if(!isOccupied(a_system, a_planet))
			return null;

		Object index1 = getCacheIndex(a_system, a_planet);
		Object index2 = new Integer(1);
		String cache = (String)getCacheEntry(index1, index2);
		if(cache == null)
		{
			cache = "Hugos Planet";
			setCacheEntry(index1, index2, cache);
		}

		return cache;
	}

	public synchronized void setPlanetName(int a_system, int a_planet, String a_name)
		throws UniverseException,IOException
	{
		checkExistance(a_system, a_planet);

		setCacheEntry(getCacheIndex(a_system, a_planet), new Integer(1), a_name);
	}

	public String getPlanetOwner(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system, a_planet);

		if(!isOccupied(a_system, a_planet))
			return null;

		Object index1 = getCacheIndex(a_system, a_planet);
		Object index2 = new Integer(2);
		String cache = (String)getCacheEntry(index1, index2);
		if(cache == null)
		{
			cache = "Hugo";
			setCacheEntry(index1, index2, cache);
		}
		return cache;
	}

	public synchronized void setPlanetOwner(int a_system, int a_planet, String a_owner)
		throws UniverseException,IOException
	{
		checkExistance(a_system, a_planet);

		setCacheEntry(getCacheIndex(a_system, a_planet), new Integer(2), a_owner);
	}

	public String getPlanetOwnerFlag(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system, a_planet);

		if(!isOccupied(a_system, a_planet))
			return null;

		Object index1 = getCacheIndex(a_system, a_planet);
		Object index2 = new Integer(3);
		String cache = (String)getCacheEntry(index1, index2);
		if(cache == null)
		{
			cache = "";
			setCacheEntry(index1, index2, cache);
		}
		return cache;
	}

	public synchronized void setPlanetOwnerFlag(int a_system, int a_planet, String a_flag)
		throws UniverseException,IOException
	{
		checkExistance(a_system, a_planet);

		setCacheEntry(getCacheIndex(a_system, a_planet), new Integer(3), a_flag);
	}

	public String getPlanetOwnerAlliance(int a_system, int a_planet)
		throws UniverseException
	{
		checkExistance(a_system, a_planet);

		if(!isOccupied(a_system, a_planet))
			return null;

		Object index1 = Integer.toString(a_system) + ":" + Integer.toString(a_planet);
		Object index2 = new Integer(4);
		String cache = (String)getCacheEntry(index1, index2);
		if(cache == null)
		{
			cache = "HUGO";
			setCacheEntry(index1, index2, cache);
		}
		return cache;
	}

	public synchronized void setPlanetOwnerAlliance(int a_system, int a_planet, String a_alliance)
		throws UniverseException,IOException
	{
		checkExistance(a_system, a_planet);

		setCacheEntry(getCacheIndex(a_system, a_planet), new Integer(4), a_alliance);
	}
}
