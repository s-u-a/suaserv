package de.cdauth.sua.suaserv;

/**
 * Provides the main application interfaces that initialises
 * all threads neaded for the serer to run.
 * @author Candid Dauth
*/

class Main
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

			// Start server thread
			new Server();
		}
		catch(Exception e)
		{
			Logger.fatal("Error initialising server", e);
		}
	}
}
