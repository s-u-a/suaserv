package de.cdauth.sua.suaserv;

class Main
{
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
		catch(Throwable e)
		{
			Logger.fatal("Error initialising server", e);
		}
	}
}
