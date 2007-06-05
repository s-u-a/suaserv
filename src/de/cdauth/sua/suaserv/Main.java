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
		System.out.println("This program is free software; you can redistribute it and/or modify it under the terms of the AFFERO GENERAL PUBLIC LICENSE as published by Affero Inc.; either version 1 of the License, or (at your option) any later version.");
		System.out.println("This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the AFFERO GENERAL PUBLIC LICENSE for more details.");
		System.out.println("You should have received a copy of the AFFERO GENERAL PUBLIC LICENSE along with this program; if not, write to Affero Inc., 510 Third Street - Suite 225, San Francisco, CA 94107, USA or have a look at http://www.affero.org/oagpl.html.");
		System.out.println("");

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
