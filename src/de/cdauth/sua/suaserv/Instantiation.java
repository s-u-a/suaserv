package de.cdauth.sua.suaserv;

import java.util.Hashtable;

/**
 * This abstract class provides basics to cache data and manages instances of a
 * class to be able to ensure that only one instance of a certain object exists.
 * @author Candid Dauth
 * @version 2.0.0
*/

public abstract class Instantiation
{
	protected static Hashtable<Class,Hashtable<String,Instantiation>> sm_instances = null;
	protected String m_id = null;
	private Hashtable m_cache = new Hashtable();

	static
	{
		sm_instances = new Hashtable<Class,Hashtable<String,Instantiation>>();
	}

	public String getID()
	{
		return m_id;
	}

	/**
	 * Returns an already existing instance of this object or creates a new one.
	 * @param a_type The type of the instantiated object.
	 * @param a_id The id of the instantiated object.
	 * @return The requested instance.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected static Instantiation getInstance(Class a_type, String a_id)
	{
		if(!sm_instances.containsKey(a_type)) return null;
		Hashtable<String,Instantiation> type = sm_instances.get(a_type);
		if(!type.containsKey(a_id)) return null;
		return type.get(a_id);
	}

	/**
	 * Adds a new instance to the instances cache.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected static void addInstance(Class a_type, Instantiation a_instance)
	{
		if(!sm_instances.containsKey(a_type))
			sm_instances.put(a_type, new Hashtable<String,Instantiation>());
		Hashtable<String,Instantiation> type = sm_instances.get(a_type);
		type.put(a_instance.getID(), a_instance);
	}

	/**
	 * Gets an entry from the object's data cache.
	 * @param a_index1 The index of the cache entry.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected Object getCacheEntry(Object a_index1)
	{
		return m_cache.get(a_index1);
	}

	/**
	 * Gets an entry from the object's data cache (second level).
	 * @param a_index1 The index of the cache entry that contains another cache Hashtable.
	 * @param a_index2 The index of the cache entry in the second level cache Hashtable.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected Object getCacheEntry(Object a_index1, Object a_index2)
	{
		Hashtable cache = (Hashtable)m_cache.get(a_index1);
		if(cache == null) return null;
		return cache.get(a_index2);
	}

	/**
	 * Stores a first level cache entry.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected void setCacheEntry(Object a_index1, Object a_value)
	{
		m_cache.put(a_index1, a_value);
	}

	/**
	 * Stores a second level cache entry.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected void setCacheEntry(Object a_index1, Object a_index2, Object a_value)
	{
		Hashtable cache = (Hashtable)m_cache.get(a_index1);
		if(cache == null)
		{
			cache = new Hashtable();
			m_cache.put(a_index1, cache);
		}
		cache.put(a_index2, a_value);
	}
}
