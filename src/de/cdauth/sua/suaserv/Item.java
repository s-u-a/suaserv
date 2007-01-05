package de.cdauth.sua.suaserv;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Objects of this class represent any item, such as a building, an object of
 * research, a robot, a ship or a defence construction.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Item extends Dataset
{
	protected RessAmount m_costs = null;
	protected long m_time = 0;
	protected ItemList m_dependencies = null;

	protected Element m_node = null;

	private static Document sm_xml = null;

	static
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			sm_xml = builder.parse(ConfigurationManager.getStringSetting("items_file"));
		}
		catch(Exception e)
		{
			Logger.error("DOM parser could not be loaded", e);
		}
	}


	/**
	 * Constructs an item object of unspecified type. The options costs,
	 * building time and dependencies are read.
	 * @param a_id The item id.
	 * @throws NumberFormatException There is an invalid numerical value in the XML file.
	 * @throws InstantiationException There is not item with the given id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public Item(String a_id)
		throws NumberFormatException,InstantiationException
	{
		m_id = a_id;

		m_node = sm_xml.getElementById(a_id);
		if(m_node == null)
			throw new InstantiationException(Item.class, a_id);

		NodeList costs_list = m_node.getElementsByTagName("costs");
		m_costs = new RessAmount();
		if(costs_list.getLength() > 0)
		{
			Element costs = (Element)costs_list.item(0);
			NodeList costs_carbon_list = costs.getElementsByTagName("carbon");
			NodeList costs_aluminium_list = costs.getElementsByTagName("aluminium");
			NodeList costs_wolfram_list = costs.getElementsByTagName("wolfram");
			NodeList costs_radium_list = costs.getElementsByTagName("radium");
			if(costs_carbon_list.getLength() > 0)
			{
				Text costs_carbon = (Text)costs_carbon_list.item(0).getFirstChild();
				if(costs_carbon != null)
					m_costs.setCarbon(Long.parseLong(costs_carbon.getData()));
			}
			if(costs_aluminium_list.getLength() > 0)
			{
				Text costs_aluminium = (Text)costs_aluminium_list.item(0).getFirstChild();
				if(costs_aluminium != null)
					m_costs.setAluminium(Long.parseLong(costs_aluminium.getData()));
			}
			if(costs_wolfram_list.getLength() > 0)
			{
				Text costs_wolfram = (Text)costs_wolfram_list.item(0).getFirstChild();
				if(costs_wolfram != null)
					m_costs.setWolfram(Long.parseLong(costs_wolfram.getData()));
			}
			if(costs_radium_list.getLength() > 0)
			{
				Text costs_radium = (Text)costs_radium_list.item(0).getFirstChild();
				if(costs_radium != null)
					m_costs.setRadium(Long.parseLong(costs_radium.getData()));
			}
		}

		NodeList time_list = m_node.getElementsByTagName("time");
		if(time_list.getLength() > 0)
		{
			Text time = (Text)time_list.item(0).getFirstChild();
			if(time != null)
				m_time = Long.parseLong(time.getData());
		}

		m_dependencies = new ItemList();
		NodeList dependencies_list = m_node.getElementsByTagName("dependencies");
		if(dependencies_list.getLength() > 0)
		{
			NodeList dependency_list = ((Element)dependencies_list.item(0)).getElementsByTagName("dependency");
			for(int i=0; i<dependency_list.getLength(); i++)
			{
				Element dep = (Element)dependency_list.item(i);
				m_dependencies.put(new Item(dep.getAttribute("id")), Long.parseLong(dep.getAttribute("level")));
			}
		}
	}


	/**
	 * Returns the costs of this item.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public RessAmount getCosts()
	{
		return m_costs;
	}


	/**
	 * Returns the building time of this item.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public long getTime()
	{
		return m_time;
	}


	/**
	 * Returns the dependencies of this item.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public ItemList getDependencies()
	{
		return m_dependencies;
	}
}