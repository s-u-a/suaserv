package de.cdauth.sua.suaserv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread
{
	protected ServerSocket m_listen_socket;
	protected ThreadGroup m_threadgroup;

	public Server()
		throws IOException
	{
		super("server");
		setPriority(ConfigurationManager.getIntSetting("server_priority"));

		m_listen_socket = new ServerSocket(ConfigurationManager.getIntSetting("server_port"));

		m_threadgroup = new ThreadGroup("clients");
		start();
	}

	public void run()
	{
		while(true) {
			try
			{
				Socket client_socket = m_listen_socket.accept();
				Connection c = new Connection(client_socket, m_threadgroup);
			}
			catch(Throwable e)
			{
				Logger.error("Could not accept connection", e);
			}
		}
	}
}
