package de.cdauth.sua.suaserv;

class Release
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
