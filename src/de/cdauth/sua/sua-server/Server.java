package de.cdauth.sua.sua-server;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;

class Server extends Thread
{
	protected ServerSocket listen_socket;
	protected ThreadGroup threadgroup;

	public void failure(Exception e, String message)
	{
		System.err.println(message+": "+e);
		System.exit(1);
	}
	
	public Server()
	{
		super("server");
		this.setPriority(SUAServer.server_priority);

		try {
			this.listen_socket = new ServerSocket(SUAServer.server_port);
		}
		catch(IOException e) {
			this.failure(e, "Couldn't create server socket.");
		}

		this.threadgroup = new ThreadGroup("clients");
		this.start();
	}

	public void run()
	{
		try {
			while(true) {
				Socket client_socket = listen_socket.accept();
				Connection c = new Connection(client_socket, threadgroup);
			}
		}
		catch(IOException e) {
			this.failure(e, "Error listening for clients.");
		}
	}
}

class Connection extends Thread
{
	protected static int connection_number_count;
	protected int connection_number;
	protected Socket client;
	protected DataInputStream in;
	protected PrintStream out;

	public Connection(Socket client_socket, ThreadGroup threadgroup)
	{
		super(threadgroup, "client " + connection_number_count++);
		this.connection_number = connection_number_count;
		this.setPriority(SUAServer.client_priority);
		this.client = client_socket;

		try {
			this.in = new DataInputStream(this.client.getInputStream());
			this.out = new PrintStream(this.client.getOutputStream());
		}
		catch(IOException e) {
			try {
				this.client.close();
			}
			catch(IOException e2) {}
			System.err.println("Error creating socket stream: "+e);
			return;
		}
		this.start();
	}

	public void run()
	{
		this.out.println("suaserv " + SUAServer.version);

		try {
			while(run_command(this.in.readLine())){}
		}
		catch(IOException e) {}
		finally {
			try {
				client.close();
			}
			catch(IOException e2) {}
		}
	}

	protected boolean run_command(String command)
	{
		if(command.equals("quit")) return false;

		this.out.println(this.connection_number + ": " + command);
		return true;
	}
}
