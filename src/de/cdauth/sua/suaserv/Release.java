package de.cdauth.sua.suaserv;

/**
 * This static class contains some constants that define the source version of the program.
 * @author Candid Dauth
 * @version 2.0.0
*/

public class Release
{
	static final private String sm_version_prefix = "";
	static final private String sm_version_major = "2";
	static final private String sm_version_minor = "0";
	static final private String sm_version_release = "0";
	static final private String sm_version_suffix = " alpha";

	public static String getVersion()
	{
		return sm_version_prefix + sm_version_major + "." + sm_version_minor + "." + sm_version_release + sm_version_suffix;
	}
}
