package de.cdauth.sua.suaserv;

class Logger
{
	public static void fatal(String a_message)
	{
		System.err.println(a_message);
		System.exit(1);
	}

	public static void fatal(String a_message, Throwable a_exception)
	{
		System.err.println(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
		System.exit(1);
	}

	public static void error(String a_message)
	{
		System.err.println(a_message);
	}

	public static void error(String a_message, Throwable a_exception)
	{
		error(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}

	public static void warning(String a_message)
	{
		if(!Options.getQuiet())
			System.err.println(a_message);
	}

	public static void warning(String a_message, Throwable a_exception)
	{
		warning(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}

	public static void debug(String a_message)
	{
		if(Options.getVerbose())
			System.err.println(a_message);
	}

	public static void debug(String a_message, Throwable a_exception)
	{
		debug(a_message + ": " + a_exception.getMessage());
		if(Options.getVerbose())
			a_exception.printStackTrace(System.err);
	}
}
