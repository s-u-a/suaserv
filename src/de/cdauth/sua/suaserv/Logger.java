package de.cdauth.sua.suaserv;

import java.util.Date;

import java.text.SimpleDateFormat;

/**
 * This static class prints any log file information to the right place.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Logger
{
	/**
	 * A fatal error occured that causes suaserv to stop.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void fatal(String a_message)
	{
		System.err.println(timePrefix() + a_message);
		System.exit(1);
	}

	public static void fatal(String a_message, Throwable a_exception)
	{
		System.err.println(timePrefix() + a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
		System.exit(1);
	}

	/**
	 * An error occured that has to be printed in any case.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void error(String a_message)
	{
		System.err.println(timePrefix() + a_message);
	}

	public static void error(String a_message, Throwable a_exception)
	{
		error(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}

	/**
	 * A warning was created that should be printed if the --quiet option was not set.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void warning(String a_message)
	{
		if(!Options.getQuiet())
			System.err.println(timePrefix() + a_message);
	}

	public static void warning(String a_message, Throwable a_exception)
	{
		warning(a_message + ": " + a_exception.getMessage());
		if(!Options.getQuiet() && Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}

	/**
	 * Displays debugging output only if the --verbose option was set.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void debug(String a_message)
	{
		if(Options.getVerbose())
			System.err.println(timePrefix() + a_message);
	}

	public static void debug(String a_message, Throwable a_exception)
	{
		debug(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}

	/**
	 * Creates a prefix string for log output that contains
	 * the time.
	 * @return "YYYY-MM-DDTHH:MM:SS -- "
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	protected static String timePrefix()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ret = format.format(new Date()) + " -- ";
		return ret;
	}
}
