package de.cdauth.sua.suaserv;

import psk.cmdline.ApplicationSettings;
import psk.cmdline.BooleanToken;
import psk.cmdline.TokenOptions;

class Options
{
	static protected ApplicationSettings sm_arguments = new ApplicationSettings();
	static protected BooleanToken sm_quiet_token = new BooleanToken("q", "Don't print warnings", "QUIET", TokenOptions.optSwitch, false);
	static protected BooleanToken sm_verbose_token = new BooleanToken("v", "Print debug output", "DEBUG", TokenOptions.optSwitch, false);
	static protected BooleanToken sm_daemon_token = new BooleanToken("d", "Fork server to run in background", "", TokenOptions.optSwitch, false);

	static
	{
		sm_arguments.addToken(sm_quiet_token);
		sm_arguments.addToken(sm_verbose_token);
		sm_arguments.addToken(sm_daemon_token);
	}

	protected static boolean sm_quiet = false;
	protected static boolean sm_verbose = false;
	protected static boolean sm_daemon = false;

	public static void load(String[] a_args)
		throws Exception
	{
		sm_arguments.parseArgs(a_args);

		sm_quiet = sm_quiet_token.getValue();
		sm_verbose = sm_verbose_token.getValue();
		sm_daemon = sm_daemon_token.getValue();
	}

	public static boolean getQuiet()
	{
		return sm_quiet;
	}

	public static boolean getVerbose()
	{
		return sm_verbose;
	}

	public static boolean getDaemon()
	{
		return sm_daemon;
	}
}
