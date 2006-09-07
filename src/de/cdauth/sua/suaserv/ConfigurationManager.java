package de.cdauth.sua.suaserv;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

class ConfigurationManager
{
	private static Properties sm_default_properties = null;
	private static Properties sm_properties = null;
	private static String sm_default_properties_file = "suaserv.defaults";
	public static String sm_properties_file = "suaserv.properties";

	static
	{
		sm_default_properties = new Properties();
		sm_properties = new Properties(sm_default_properties);
	}

	public static void load()
		throws IOException,FileNotFoundException
	{
		sm_default_properties.load(new FileInputStream(sm_default_properties_file));
		sm_properties.load(new FileInputStream(sm_properties_file));
	}

	public static String getStringSetting(String a_name)
	{
		return sm_properties.getProperty(a_name);
	}

	public static int getIntSetting(String a_name)
		throws NumberFormatException
	{
		return Integer.parseInt(sm_properties.getProperty(a_name));
	}
}
