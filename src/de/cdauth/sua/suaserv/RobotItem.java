package de.cdauth.sua.suaserv;

/**
 * This class represents a special type of item: a robot. A robot doesn't have any special
 * properties compared to the properties that all items have.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class RobotItem extends Item
{
	/**
	 * Constructs a robot item.
	 * @param a_id The item id.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public RobotItem(String a_id)
		throws InstantiationException
	{
		super(a_id);
	}
}