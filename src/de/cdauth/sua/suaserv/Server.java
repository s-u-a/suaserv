package de.cdauth.sua.suaserv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server thread that listens on the specified port and
 * starts new threads for each new connection
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Server extends Thread
{
	protected ServerSocket m_listen_socket;
	protected ThreadGroup m_threadgroup;

	public Server()
	{
		super("server");
		setPriority(ConfigurationManager.getIntSetting("server_priority"));

		Logger.debug("Starting Server thread");

		start();
	}

	public void run()
	{
		try
		{
			Logger.debug("This is the Server thread");

			m_listen_socket = new ServerSocket(ConfigurationManager.getIntSetting("server_port"));
			m_threadgroup = new ThreadGroup("clients");
		}
		catch(Exception e)
		{
			Logger.fatal("Exception in Server thread", e);
		}

		while(true) {
			try
			{
				Socket client_socket = m_listen_socket.accept();
				Connection c = new Connection(client_socket, m_threadgroup);
			}
			catch(Exception e)
			{
				Logger.error("Could not accept connection", e);
			}
		}
	}
}
