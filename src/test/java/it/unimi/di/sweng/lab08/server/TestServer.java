package it.unimi.di.sweng.lab08.server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.restlet.resource.ResourceException;

import it.unimi.di.sweng.lab08.example.mock.MockClient;

public class TestServer {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private final static int PORT = 8000;

	private static Server server;
	private static MockClient mockClient;

	@BeforeClass
	public static void setUp() throws Exception {
		mockClient = new MockClient(PORT);
		server = new Server(PORT);
		server.start();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		server.stop();
	}
	
	
	
}
