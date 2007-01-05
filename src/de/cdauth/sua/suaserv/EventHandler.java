package de.cdauth.sua.suaserv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;

/**
 * This class starts a thread that processes all incoming events every second.
 * This can be finishing building items or making fleets arrive.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class EventHandler extends Thread
{
	public EventHandler()
	{
		super("eventhandler");
		setPriority(ConfigurationManager.getIntSetting("eventhandler_priority"));
		setDaemon(true);

		Logger.debug("Starting EventHandler thread");

		start();
	}

	public void run()
	{
		Logger.debug("This is the EventHandler thread");

		try
		{
			Connection sql = Database.getConnection();
			PreparedStatement stmt;
			ResultSet res;
			Calendar now;

			while(true)
			{
				try
				{
					stmt = sql.prepareStatement("SELECT * FROM t_building_items WHERE c_next_finishing_time <= 'now' ORDER BY c_id ASC");
					res = stmt.executeQuery();
				}
				catch(Exception e)
				{
					Logger.error("Exception in EventHandler thread", e);
				}
				finally
				{
					try
					{
						Thread.sleep(1000);
					}
					catch(InterruptedException e)
					{
						Logger.debug("InterruptedException in Daemon.run()", e);
					}
				}
			}
		}
		catch(Exception e)
		{
			Logger.fatal("Exception initialising EventHandler thread", e);
		}
	}
}