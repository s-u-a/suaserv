package de.cdauth.sua.suaserv;

/**
 * Every instance of this class represents some information about a
 * client, such as the user agent, the client id, and the current user.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class ClientOptions
{
	protected String m_client = null;
	protected String m_agent = null;
	protected String m_user = null;

	public String getClient()
	{
		return m_client;
	}

	public void setClient(String a_client)
	{
		m_client = a_client;
	}

	public String getAgent()
	{
		return m_agent;
	}

	public void setAgent(String a_agent)
	{
		m_agent = a_agent;
	}

	public String getUser()
	{
		return m_user;
	}

	public void setUser(String a_user)
	{
		m_user = a_user;
	}
}
