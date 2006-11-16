package de.cdauth.sua.suaserv;

import psk.cmdline.ApplicationSettings;
import psk.cmdline.BooleanToken;
import psk.cmdline.StringToken;
import psk.cmdline.TokenOptions;

/**
 * This static class manages all passed command line options.
 * @author Candid Dauth
 * @version 2.0.0
*/

class Options
{
	static protected ApplicationSettings sm_arguments = new ApplicationSettings();
	static protected BooleanToken sm_quiet_token = new BooleanToken("q", "Don't print warnings", "QUIET", TokenOptions.optSwitch, false);
	static protected BooleanToken sm_verbose_token = new BooleanToken("v", "Print debug output", "DEBUG", TokenOptions.optSwitch, false);
	static protected StringToken sm_config_token = new StringToken("c", "The suaserv configuration file", "", 0, "suaserv.properties");
	static protected StringToken sm_defaults_token = new StringToken("B", "The defaults configuration file", "", 0, "/etc/suaserv.defaults");
	static protected StringToken sm_database_token = new StringToken("d", "The database directory", "SUASERV_DATABASE", TokenOptions.optRequired, null);

	static
	{
		sm_arguments.addToken(sm_quiet_token);
		sm_arguments.addToken(sm_verbose_token);
		sm_arguments.addToken(sm_config_token);
		sm_arguments.addToken(sm_defaults_token);
		sm_arguments.addToken(sm_database_token);
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
		sm_arguments.parseArgs(a_args);

		sm_quiet = sm_quiet_token.getValue();
		sm_verbose = sm_verbose_token.getValue();
		sm_config = sm_config_token.getValue();
		sm_defaults = sm_defaults_token.getValue();
		sm_database = sm_database_token.getValue();
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
