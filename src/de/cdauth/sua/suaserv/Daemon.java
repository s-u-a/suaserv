package de.cdauth.sua.suaserv;

import java.util.Calendar;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * This class provides a low-priority thread that processes several tasks
 * that have to be done every night, such as recalculating the exchange
 * rate or notifying inactive users about their account near account
 * deletion.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Daemon extends Thread
{
	private int m_last_day;
	private int m_last_year;

	public Daemon()
	{
		super("daemon");
		setPriority(ConfigurationManager.getIntSetting("daemon_priority"));
		setDaemon(true);

		Logger.debug("Starting Daemon thread");

		start();
	}

	public void run()
	{
		Logger.debug("This is the Daemon thread");

		while(true)
		{
			try
			{
				Calendar now = Calendar.getInstance();
				if(now.get(Calendar.HOUR_OF_DAY) < ConfigurationManager.getIntSetting("daemon_hour"))
					continue;
				PreparedStatement stmt = Database.getConnection().prepareStatement("SELECT c_date FROM t_exchange_rate ORDER BY c_date DESC LIMIT 1");
				ResultSet res = stmt.executeQuery();

				if(res.next())
				{
					Calendar last_run = Calendar.getInstance();
					last_run.setTime(res.getDate("c_date"));
					if(last_run.get(Calendar.YEAR) > now.get(Calendar.YEAR))
					{
						Logger.warning("Last exchange rate recalculation is in the future");
						continue;
					}
					else if(last_run.get(Calendar.YEAR) == now.get(Calendar.YEAR))
					{
						if(last_run.get(Calendar.DAY_OF_YEAR) > now.get(Calendar.DAY_OF_YEAR))
						{
							Logger.warning("Last exchange rate recalculation is in the future");
							continue;
						}
						else if(last_run.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR))
						{
							Logger.debug("Exchange rate was calculated today, not recalculating");
							continue;
						}
					}
				}

				Logger.debug("Recalculating exchange rate");
			}
			catch(SQLException e)
			{
				Logger.warning("SQL error recalculating the exchange rate", e);
			}
			catch(Exception e)
			{
				Logger.warning("Exception in Daemon thread", e);
			}
			finally // Wait 10 minutes between checks
			{
				try
				{
					Thread.sleep(600000);
				}
				catch(InterruptedException e)
				{
					Logger.debug("InterruptedException in Daemon.run()", e);
				}
			}
		}
	}
}