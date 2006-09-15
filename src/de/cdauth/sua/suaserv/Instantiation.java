package de.cdauth.sua.suaserv;

import java.util.Hashtable;

abstract class Instantiation
{
	protected static Hashtable sm_instances = null;
	protected String m_id = null;
	private Hashtable m_cache = new Hashtable();

	static
	{
		sm_instances = new Hashtable();
	}

	public String getID()
	{
		return (m_id == null) ? null : new String(m_id);
	}

	protected static Object getInstance(Class a_type, String a_id)
	{
		if(!sm_instances.containsKey(a_type)) return null;
		Hashtable type = (Hashtable) sm_instances.get(a_type);
		if(!type.containsKey(a_id)) return null;
		return type.get(a_id);
	}

	protected static void addInstance(Class a_type, String a_id, Object a_instance)
	{
		if(!sm_instances.containsKey(a_type))
			sm_instances.put(a_type, new Hashtable());
		Hashtable type = (Hashtable) sm_instances.get(a_type);
		type.put(a_id, a_instance);
	}

	protected Object getCacheEntry(Object a_index1)
	{
		return m_cache.get(a_index1);
	}

	protected Object getCacheEntry(Object a_index1, Object a_index2)
	{
		Hashtable cache = (Hashtable)m_cache.get(a_index1);
		if(cache == null) return null;
		return cache.get(a_index2);
	}

	protected void setCacheEntry(Object a_index1, Object a_value)
	{
		m_cache.put(a_index1, a_value);
	}

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
