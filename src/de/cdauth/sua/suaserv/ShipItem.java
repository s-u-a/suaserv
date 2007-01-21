package de.cdauth.sua.suaserv;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ShipItem extends BattleItem
{
	private long m_transport_ressources = 0;
	private long m_transport_robots = 0;
	private long m_speed = 0;

	public ShipItem(String a_id)
		throws InstantiationException
	{
		super(a_id);

		NodeList capacity_list = m_node.getElementsByTagName("capacity");
		if(capacity_list.getLength() > 0)
		{
			Element capacity = (Element)capacity_list.item(0);
			NodeList ressources_list = capacity.getElementsByTagName("ressources");
			if(ressources_list.getLength() > 0)
			{
				Text ressources = (Text)ressources_list.item(0);
				if(ressources != null)
					m_transport_ressources = Long.parseLong(ressources.getData());
			}
			NodeList robots_list = capacity.getElementsByTagName("robots");
			if(robots_list.getLength() > 0)
			{
				Text robots = (Text)robots_list.item(0);
				if(robots != null)
					m_transport_robots = Long.parseLong(robots.getData());
			}
		}

		NodeList speed_list = m_node.getElementsByTagName("speed");
		if(speed_list.getLength() > 0)
		{
			Text speed = (Text)speed_list.item(0).getFirstChild();
			if(speed != null)
				m_speed = Long.parseLong(speed.getData());
		}
	}
}