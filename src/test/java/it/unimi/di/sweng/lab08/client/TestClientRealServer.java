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
		assertEquals("{inizio=8:30, fine=12:30}", client.job("university").toString());
		client.newJob("music", "14:30");
		assertEquals("[music, university]", client.jobs().toString());
	}
	
	@Test
	public void testNoJobs() throws Exception {
		try {
			client.jobs();
		} catch (Exception e) {
			assertEquals("There are no Jobs", e.getMessage());
		}
	}
	
	@Test
	public void testAbsentJob() throws Exception {
		try {
			client.job("shopping");
		} catch (Exception e) {
			assertEquals("There is no shopping JOB", e.getMessage());
		}
	}
	
	@Test
	public void testJobStatistics() throws Exception {
		client.newJob("pianoforte", "01:30");;
		client.endJob("pianoforte", "20:30");
		client.setStatistics(new MaxJob(PORT));
		assertEquals("The longest job is music with a duration of 19.0 hours\n", client.printStatistics());
	}
}
