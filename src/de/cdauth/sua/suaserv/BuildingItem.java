package de.cdauth.sua.suaserv;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * This class represents a special type of items: a building. A building has
 * one property that not every item has: production.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class BuildingItem extends Item
{
	private ProductionAmount m_production = null;

	/**
	 * Constructs a building item object reading the default item properties plus the production
	 * from the XML file.
	 * @param a_id The item id.
	 * @throws NumberFormatException A numerical value in the XML file is invalid.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public BuildingItem(String a_id)
		throws InstantiationException
	{
		super(a_id);

		NodeList production_list = m_node.getElementsByTagName("production");
		m_production = new ProductionAmount();
		if(production_list.getLength() > 0)
		{
			Element production = (Element)production_list.item(0);
			NodeList production_carbon_list = production.getElementsByTagName("carbon");
			NodeList production_aluminium_list = production.getElementsByTagName("aluminium");
			NodeList production_wolfram_list = production.getElementsByTagName("wolfram");
			NodeList production_radium_list = production.getElementsByTagName("radium");
			NodeList production_tritium_list = production.getElementsByTagName("tritium");
			NodeList production_power_list = production.getElementsByTagName("power");
			if(production_carbon_list.getLength() > 0)
			{
				Text production_carbon = (Text)production_carbon_list.item(0).getFirstChild();
				if(production_carbon != null)
					m_production.setCarbon(Long.parseLong(production_carbon.getData()));
			}
			if(production_aluminium_list.getLength() > 0)
			{
				Text production_aluminium = (Text)production_aluminium_list.item(0).getFirstChild();
				if(production_aluminium != null)
					m_production.setAluminium(Long.parseLong(production_aluminium.getData()));
			}
			if(production_wolfram_list.getLength() > 0)
			{
				Text production_wolfram = (Text)production_wolfram_list.item(0).getFirstChild();
				if(production_wolfram != null)
					m_production.setWolfram(Long.parseLong(production_wolfram.getData()));
			}
			if(production_radium_list.getLength() > 0)
			{
				Text production_radium = (Text)production_radium_list.item(0).getFirstChild();
				if(production_radium != null)
					m_production.setRadium(Long.parseLong(production_radium.getData()));
			}
			if(production_tritium_list.getLength() > 0)
			{
				Text production_tritium = (Text)production_tritium_list.item(0).getFirstChild();
				if(production_tritium != null)
					m_production.setTritium(Long.parseLong(production_tritium.getData()));
			}
			if(production_power_list.getLength() > 0)
			{
				Text production_power = (Text)production_power_list.item(0).getFirstChild();
				if(production_power != null)
					m_production.setPower(Long.parseLong(production_power.getData()));
			}
		}
	}

	public ProductionAmount getProduction()
	{
		return m_production;
	}
}