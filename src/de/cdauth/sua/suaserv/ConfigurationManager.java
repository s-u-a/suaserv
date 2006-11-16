package de.cdauth.sua.suaserv;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * This static class manages the current server configuration by using
 * the specified configuration files.
 * @author Candid Dauth
 * @version 2.0.0
*/

class ConfigurationManager
{
	private static Properties sm_default_properties = null;
	private static Properties sm_properties = null;
	private static String sm_default_properties_file = null;
	public static String sm_properties_file = null;

	static
	{
		sm_default_properties = new Properties();
		sm_properties = new Properties(sm_default_properties);
	}

	/**
	 * Loads the configuration from the specified configuration files.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static void load()
		throws IOException,FileNotFoundException
	{
		sm_default_properties_file = Options.getDefaults();
		sm_properties_file = Options.getConfig();
		sm_default_properties.load(new FileInputStream(sm_default_properties_file));
		sm_properties.load(new FileInputStream(sm_properties_file));
	}

	/**
	 * Gets a string from the configuration file, if it is not defined,
	 * the default configuration file is used.
	 * @param a_name The name of the setting.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static String getStringSetting(String a_name)
	{
		String property = sm_properties.getProperty(a_name);
		if(property != null)
			property = new String(property);
		return property;
	}

	/**
	 * Gets an integer setting from the configuration file, if it is not
	 * set, the default configuration file is used.
	 * @param a_name The name of the setting.
	 * @throws NumberFormatException The value of the specified setting is not a number.
	 * @author Candid Dauth
	 * @version 2.0.0
	*/

	public static int getIntSetting(String a_name)
		throws NumberFormatException
	{
		return Integer.parseInt(sm_properties.getProperty(a_name));
	}
}
