package de.cdauth.sua;

class SUAServer
{
	protected static String version = "2.0.0 alpha";
	protected static int server_port = 6231;
	protected static int server_priority = 5;
	protected static int client_priority = 3;

	public static void main(String[] args)
	{
		new Server();
	}
}
