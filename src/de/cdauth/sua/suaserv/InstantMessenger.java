package de.cdauth.sua.suaserv;

/**
 * This class starts a thread that notifies users about incoming events.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class InstantMessenger extends Thread
{
	public InstantMessenger()
	{
		super("instantmessenger");
		setPriority(ConfigurationManager.getIntSetting("instantmessenger_priority"));
		setDaemon(true);

		Logger.debug("Starting InstantMessenger thread");

		start();
	}

	public void run()
	{
		Logger.debug("This is the InstantMessenger thread");

		while(true)
		{
			try
			{
			}
			catch(Exception e)
			{
				Logger.warning("Exception in InstantMessenger thread", e);
			}
			finally
			{
				try
				{
					Thread.sleep(600000);
				}
				catch(InterruptedException e)
				{
					Logger.debug("InterruptedException in InstantMessenger.run()", e);
				}
			}
		}
	}
}