package de.cdauth.sua.suaserv;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class BattleItem extends Item
{
	private long m_attack = 0;
	private long m_defence = 0;

	public BattleItem(String a_id)
		throws InstantiationException
	{
		super(a_id);

		NodeList strength_list = m_node.getElementsByTagName("strength");
		if(strength_list.getLength() > 0)
		{
			Element strength = (Element)strength_list.item(0);
			NodeList attack_list = strength.getElementsByTagName("attack");
			if(attack_list.getLength() > 0)
			{
				Text attack = (Text)attack_list.item(0);
				if(attack != null)
					m_attack = Long.parseLong(attack.getData());
			}
			NodeList defence_list = strength.getElementsByTagName("defence");
			if(defence_list.getLength() > 0)
			{
				Text defence = (Text)defence_list.item(0);
				if(defence != null)
					m_defence = Long.parseLong(defence.getData());
			}
		}
	}

	public long getAttack()
	{
		return m_attack;
	}

	public long getDefence()
	{
		return m_defence;
	}
}