package de.cdauth.sua.suaserv;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

/**
 * Represents a list of items, each of which a number is assigned to.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class ItemList extends Hashtable<Item, Long>
{
	/**
	 * Constructs an ItemList object out of a easily-storable string.
	 * @param a_list A string of the form &lt;ID&gt; &lt;Number&gt; &lt;ID&gt; &lt;Number&gt; ...
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public ItemList(String a_list)
		throws NumberFormatException,IllegalArgumentException
	{
		super();
		String[] parts = a_list.split(" ");
		if(parts.length % 2 != 0)
			throw new IllegalArgumentException("This is not a valid ItemList.");
		for(int i=0; i<parts.length; i+=2)
			put(new Item(parts[i]), Long.parseLong(parts[i+1]));
	}

	public ItemList()
	{
		super();
	}

	public ItemList(int a_initial_capacity)
	{
		super(a_initial_capacity);
	}

	public ItemList(int a_initial_capacity, float a_load_factor)
	{
		super(a_initial_capacity, a_load_factor);
	}

	public ItemList(Map<Item, Long> a_t)
	{
		super(a_t);
	}

	/**
	 * Converts the ItemList object to a easily-storable string.
	 * @return A string of the form &lt;ID&gt; &lt;Number&gt; &lt;ID&gt; &lt;Number&gt; ...
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		Enumeration<Item> keys = keys();
		while(keys.hasMoreElements())
		{
			Item i = keys.nextElement();
			sb.append((first ? "" : " ") + i.getID() + " " + get(i).toString());
			first = false;
		}
		return sb.toString();
	}

	public Long get(Object a_key)
	{
		Long res = super.get(a_key);
		if(res == null)
			res = new Long(0);
		return res;
	}
}