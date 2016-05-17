package it.unimi.di.sweng.lab08.client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.lab08.server.Server;

public class TestClientRealServer {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private final static int PORT = 8000;

	private static Client client;
	private static Server server;

	@BeforeClass
	public static void setUp() throws Exception {
		client = new Client(PORT);
		server = new Server(PORT);
		server.start();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		server.stop();
	}
	
	@Test
	public void testnewJob() throws Exception {
		client.newJob("university", "8:30");
		assertEquals("[university]", client.jobs().toString());
		client.endJob("university", "12:30");
		assertEquals("university: start=8:30 | end=12:30", client.job("university"));
		client.newJob("music", "14:30");
		assertEquals("[music, university]", client.jobs().toString());
	}
}
