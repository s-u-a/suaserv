package de.cdauth.sua.suaserv;

import java.io.Reader;
import java.io.Writer;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.util.Vector;

/**
 * Provides a thread that is created for each connected client.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Connection extends Thread
{
	protected static int sm_connection_number_count = 0;
	protected int m_connection_number;
	protected Socket m_client;
	protected Reader m_in;
	protected Writer m_out;
	protected ClientOptions m_options;

	/**
	 * The commands can easily be renamed here, but the order is important
	 * and has to be changed in sm_commands_parameters and runCommand, too.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected static String[] sm_commands = {
		"quit",         // 0
		"agent",        // 1
		"client",       // 2
		"user",         // 3
		"galaxy"        // 4
	};

	/**
	 * This static member defines how many parameters a certain
	 * command wants to have, if a smaller count is passed, an
	 * exception is thrown.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected static int[] sm_commands_parameters = {
		0,	// quit
		1,	// agent
		1,	// client
		2,	// user
		0	// galaxy
	};

	/**
	 * Initialises the new client thread.
	 * @param a_client_socket The client socket returned by java.net.ServerSocket.accept().
	 * @param a_threadgroup The thread group for all client connections.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public Connection(Socket a_client_socket, ThreadGroup a_threadgroup)
	{
		super(a_threadgroup, "client " + sm_connection_number_count++);
		m_connection_number = sm_connection_number_count;
		setPriority(ConfigurationManager.getIntSetting("client_priority"));
		m_client = a_client_socket;

		try {
			m_in = new InputStreamReader(m_client.getInputStream(), Charset.forName(ConfigurationManager.getStringSetting("charset")));
			m_out = new OutputStreamWriter(m_client.getOutputStream(), Charset.forName(ConfigurationManager.getStringSetting("charset")));
		}
		catch(IOException e) {
			try {
				m_client.close();
			}
			catch(IOException e2) {}
			Logger.error("Error creating socket stream", e);
			return;
		}

		m_options = new ClientOptions();

		Logger.debug("Client " + m_connection_number + " connected.");

		start();
	}

	/**
	 * This method is responsible for the communication between client
	 * and server. It reads the commands that the client sends, parses
	 * them, and passes them to the command processing method.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public void run()
	{
		try
		{
			println("suaserv " + Release.getVersion());

			String line;
			StringBuffer command_buffer = new StringBuffer();
			Vector<StringBuffer> arguments_buffer = new Vector<StringBuffer>();
			int i = -1;
			boolean escaped = false;
			boolean next = false;
			char c;

			connection:while(true)
			{
				c = readChar();
				if(!escaped && c == '\n')
				{
					if(!parseCommand(command_buffer, arguments_buffer))
						break connection;
					command_buffer.setLength(0);
					arguments_buffer.removeAllElements();
					i = -1;
					next = false;
				}
				else if(!escaped && Character.isISOControl(c)); // Ignore non-printable signs
				else if(!escaped && c == ' ')
					next = true;
				else if(!escaped && c == '\\')
					escaped = true;
				else
				{
					escaped = false;
					if(next)
					{
						i++;
						arguments_buffer.addElement(new StringBuffer());
						next = false;
					}

					StringBuffer this_buffer = (i == -1) ? command_buffer : (StringBuffer)arguments_buffer.elementAt(i);
					this_buffer.append(c);
				}
			}
		}
		catch(EOFException e)
		{
			Logger.debug("Client " + m_connection_number + " quit improperly" + (m_options.getAgent() == null ? "" : " (agent "+m_options.getAgent()+")") + ".");
		}
		catch(IOException e)
		{
			Logger.debug("Client " + m_connection_number + " died because of an IOException.");
		}
		catch(Exception e)
		{
			Logger.error("Uncaught exception in client "+Integer.toString(m_connection_number), e);
		}
		finally {
			try {
				m_client.close();
				Logger.debug("Closed connection to client " + m_connection_number + (m_options.getAgent() == null ? "" : " (agent "+m_options.getAgent()+")") + ".");
			}
			catch(IOException e2) {}
		}
	}

	/**
	 * Converts the passed buffer elements to usable data types and calls
	 * the command processing method.
	 * @param a_command A string buffer that contains the command name.
	 * @param a_arguments A vector that contains StringBuffer objects with the arguments passed with the command.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected boolean parseCommand(StringBuffer a_command, Vector<StringBuffer> a_arguments)
		throws IOException
	{
		String command = a_command.toString();
		String[] arguments = new String[a_arguments.size()];
		for(int i=0; i<a_arguments.size(); i++)
			arguments[i] = a_arguments.elementAt(i).toString();
		return runCommand(command, arguments);
	}

	/**
	 * Ensures that the client is logged in.
	 * @throws AccessViolationException The client is not logged in.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected void requireAuth()
		throws AccessViolationException
	{
		if(m_options.getUser() == null)
			throw new AccessViolationException("Login required.");
	}

	/**
	 * Calls all needed functions for a command to be run.
	 * @param a_command The command that should be executed.
	 * @param a_arguments A string array of all passed arguments.
	 * @return Whether the command could successfully be executed.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected boolean runCommand(String a_command, String[] a_arguments)
		throws IOException
	{
		if(sm_commands.length != sm_commands_parameters.length)
			throw new NoSuchFieldError("sm_commands.length and sm_commands_parameters.length are different in de.cdauth.sua.suaserv.Connection.");

		try
		{
			int command = 0;
			boolean command_found = false;
			for(int i=0; i<sm_commands.length; i++)
			{
				if(sm_commands[i].equals(a_command))
				{
					command = i;
					command_found = true;
					break;
				}
			}
			if(!command_found)
			{
				sendStatusCode(2, 0); // Command not found
				return true;
			}
			if(a_arguments.length < sm_commands_parameters[command])
			{
				sendStatusCode(2, 1); // Insufficient arguments
				return true;
			}

			switch(command)
			{
				case 0: // quit
					return false;
				case 1: // agent
					if(m_options.getAgent() != null)
					{
						sendStatusCode(2, 0);
						break;
					}
					m_options.setAgent(a_arguments[0]);
					sendStatusCode(1, 0);
					break;
				case 2: // client
					m_options.setClient(a_arguments[0]);
					sendStatusCode(1, 0);
					break;
				case 3: // user
					if(m_options.getAgent() == null || m_options.getClient() == null)
					{
						sendStatusCode(2, 2);
						break;
					}
					try
					{
						String username = User.resolveName(a_arguments[0]);
						User me = User.getInstance(username);
						if(me.checkPassword(a_arguments[1]))
						{
							m_options.setUser(username);
							sendStatusCode(1, 0);
							break;
						}
					}
					catch(UserException e){}
					sendStatusCode(2, 6);
					break;
				case 4: // galaxy
					requireAuth();
					try
					{
						if(a_arguments.length >= 1)
						{
							Galaxy galaxy = Galaxy.getInstance(a_arguments[0]);
							if(a_arguments.length >= 3) // 3 arguments: print planet information
							{
								int system = Integer.parseInt(a_arguments[1]);
								int planet = Integer.parseInt(a_arguments[2]);
								if(galaxy.isOccupied(system, planet))
								{
									sendData(galaxy.getPlanetName(system, planet));
									sendData(galaxy.getPlanetOwner(system, planet));
									sendData(galaxy.getPlanetOwnerFlag(system, planet));
									sendData(galaxy.getPlanetOwnerAlliance(system, planet));
								}
								else
									sendStatusCode(3, 0);
							}
							else if(a_arguments.length >= 2) // 2 arguments: print planet count in passed system
							{
								int system = Integer.parseInt(a_arguments[1]);
								sendData(Integer.toString(galaxy.getPlanetCount(system)));
							}
							else // 1 argument: print system count in passed galaxy
								sendData(Integer.toString(galaxy.getSystemCount()));
						}
						else // 0 arguments: print galaxy count in the universe
							sendData(Integer.toString(Galaxy.getGalaxyCount()));
					}
					catch(UniverseException e)
					{
						sendStatusCode(2, 7); // Location not found in the universe
					}
					break;
			}
		}
		catch(IOException e)
		{
			sendStatusCode(2, 4); // I/O error
			Logger.error("IOException on client "+Integer.toString(m_connection_number), e);
		}
		catch(NumberFormatException e)
		{
			sendStatusCode(2, 5); // Invalid number format error
		}
		catch(AccessViolationException e)
		{
			sendStatusCode(2, 3); // Access violation error
		}

		return true;
	}

	/**
	 * Sends the needed output lines to the client to transmit a status code.
	 * @param a_major The major status code number.
	 * @param a_minor The minor status code number.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public void sendStatusCode(int a_major, int a_minor)
		throws IOException
	{
		println(Integer.toString(a_major)+" "+Integer.toString(a_minor));
	}

	/**
	 * Prepares and performs raw data transmission to the client.
	 * @param a_data The data that should be sent.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public void sendData(String a_data)
		throws IOException
	{
		sendStatusCode(0, a_data.length());
		println(a_data);
	}

	/**
	 * Sends the specified string to the client, followed by a line break.
	 * @param a_line The string that should be sent.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected void println(String a_line)
		throws IOException
	{
		m_out.write(a_line+"\n");
		m_out.flush();
	}

	/**
	 * Reads one character from the client, ensuring that the specified charset is used.
	 * @return The read character.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected char readChar()
		throws IOException,EOFException
	{
		char[] c = new char[1];
		if(m_in.read(c, 0, 1) == -1)
			throw new EOFException("Connection closed.");
		while(c.length < 1)
		{
			try
			{
				Thread.sleep(200);
			}
			catch(InterruptedException e)
			{
				Logger.debug("InterruptedException in Connection.readChar()", e);
			}
			m_in.read(c, 0, 1);
		}
		return c[0];
	}
}
