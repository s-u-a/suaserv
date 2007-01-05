package de.cdauth.sua.suaserv;

import de.cdauth.cmdargs.Argument;
import de.cdauth.cmdargs.ArgumentParser;
import de.cdauth.cmdargs.ArgumentParserException;

/**
 * This static class manages all passed command line options.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Options
{
	static protected ArgumentParser sm_arguments = null;
	static protected Argument sm_quiet_token = null;
	static protected Argument sm_verbose_token = null;
	static protected Argument sm_config_token = null;
	static protected Argument sm_defaults_token = null;
	static protected Argument sm_database_token = null;

	static
	{
		try
		{
			sm_arguments = new ArgumentParser("suaserv");

			sm_quiet_token = new Argument('q', "quiet");
			sm_quiet_token.setDescription("Don't print warnings");
			sm_arguments.addArgument(sm_quiet_token);

			sm_verbose_token = new Argument('v', "verbose");
			sm_verbose_token.setDescription("Print debug output");
			sm_arguments.addArgument(sm_verbose_token);

			sm_config_token = new Argument('c', "config");
			sm_config_token.setDescription("The suaserv configuration file");
			sm_config_token.setParameter(Argument.PARAMETER_REQUIRED);
			sm_arguments.addArgument(sm_config_token);

			sm_defaults_token = new Argument('B', "default-config");
			sm_defaults_token.setDescription("The defaults configuration file");
			sm_defaults_token.setParameter(Argument.PARAMETER_REQUIRED);
			sm_arguments.addArgument(sm_defaults_token);

			sm_database_token = new Argument('d', "database");
			sm_database_token.setDescription("The database directory");
			sm_database_token.setParameter(Argument.PARAMETER_REQUIRED);
			sm_database_token.setRequired(true);
			sm_arguments.addArgument(sm_database_token);
		}
		catch(ArgumentParserException e){}
	}

	static protected boolean sm_quiet = false;
	static protected boolean sm_verbose = false;
	static protected String sm_config = null;
	static protected String sm_defaults = null;
	static protected String sm_database = null;

	/**
	 * Reads the passed command line options and saves them into variables.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void load(String[] a_args)
		throws Exception
	{
		sm_arguments.parseArguments(a_args);

		sm_quiet = sm_quiet_token.set() > 0;
		sm_verbose = sm_verbose_token.set() > 0;
		sm_config = sm_config_token.parameter();
		if(sm_config == null) sm_config = "suaserv.properties";
		sm_defaults = sm_defaults_token.parameter();
		if(sm_defaults == null) sm_defaults = "/etc/suaserv/suaserv.defaults.xml";
		sm_database = sm_database_token.parameter();
	}

	public static boolean getQuiet()
	{
		return sm_quiet;
	}

	public static boolean getVerbose()
	{
		return sm_verbose;
	}

	public static String getConfig()
	{
		return sm_config;
	}

	public static String getDefaults()
	{
		return sm_defaults;
	}

	public static String getDatabase()
	{
		return sm_database;
	}
}
