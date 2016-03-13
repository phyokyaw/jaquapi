package net.phyokyaw.jaquapi;

public class Main
{
	public static void main(String... anArgs) throws Exception
	{
		new Main().start();
	}

	private final WebServer server;

	public Main()
	{
		server = new WebServer(8080);
	}

	public void start() throws Exception
	{
		server.start();
		server.join();
	}
}
