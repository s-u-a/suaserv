package de.cdauth.sua.suaserv;

/**
 * Provides the main application interfaces that initialises
 * all threads neaded for the serer to run.
 * @author Candid Dauth
*/

public class Main
{
	/**
	 * The main method is called when suaserv is started.
	*/

	public static void main(String[] a_args)
	{
		try
		{
			// Load command-line arguments
			Options.load(a_args);

			// Load properties file
			ConfigurationManager.load();

			// Open database connection
			Database.initConnection();

			// Start server thread
			new Server();

			// Start eventhandler thread
			new EventHandler();

			// Start daemon thread
			new Daemon();

			// Start IM thread
			new InstantMessenger();
		}
		catch(Exception e)
		{
			Logger.fatal("Error initialising server", e);
		}
	}
}
