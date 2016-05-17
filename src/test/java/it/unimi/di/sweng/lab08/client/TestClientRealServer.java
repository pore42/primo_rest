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
		client.newJob("university", "08:30");
		client.endJob("university", "12:30");
		client.newJob("music", "14:30");
		client.newJob("reading", "01:30");
		client.endJob("reading", "20:00");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		server.stop();
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
		
		client.setStatistics(new LongestJob(PORT));
		assertEquals("The longest job is reading with a duration of 18.5 hours\n", client.printStatistics());
	}
	
	@Test
	public void testShortestJob() throws Exception {
		client.setStatistics(new ShortestJob(PORT));
		assertEquals("The shortest job is university with a duration of 4.0 hours\n", client.printStatistics());
	}
	
	@Test
	public void testAverageJob() throws Exception {
		client.setStatistics(new AverageJobs(PORT));
		assertEquals("The average duration of a job is of 6.17 h\n", client.printStatistics());
	}
}
