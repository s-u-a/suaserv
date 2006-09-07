package de.cdauth.sua.suaserv;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Provides a thread that is created for each connected client.
 * @author Candid Dauth
*/

class Connection extends Thread
{
	protected static int sm_connection_number_count = 0;
	protected int m_connection_number;
	protected Socket m_client;
	protected BufferedReader m_in;
	protected PrintStream m_out;

	public Connection(Socket a_client_socket, ThreadGroup a_threadgroup)
	{
		super(a_threadgroup, "client " + sm_connection_number_count++);
		m_connection_number = sm_connection_number_count;
		setPriority(ConfigurationManager.getIntSetting("client_priority"));
		m_client = a_client_socket;

		try {
			m_in = new BufferedReader(new InputStreamReader(m_client.getInputStream()));
			m_out = new PrintStream(m_client.getOutputStream());
		}
		catch(IOException e) {
			try {
				m_client.close();
			}
			catch(IOException e2) {}
			Logger.error("Error creating socket stream", e);
			return;
		}
		this.start();
	}

	public void run()
	{
		m_out.println("suaserv " + Release.getVersion());

		try {
			while(run_command(m_in.readLine())){}
		}
		catch(IOException e) {}
		finally {
			try {
				m_client.close();
			}
			catch(IOException e2) {}
		}
	}

	protected boolean run_command(String a_command)
	{
		if(a_command.equals("quit")) return false;

		m_out.println(m_connection_number + ": " + a_command);
		return true;
	}
}
