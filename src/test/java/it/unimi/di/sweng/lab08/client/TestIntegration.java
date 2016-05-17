package it.unimi.di.sweng.lab08.client;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.lab08.client.Client;
import it.unimi.di.sweng.lab08.model.Job;
import it.unimi.di.sweng.lab08.server.Server;

public class TestIntegration {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private final static int PORT = 8000;
	private static Server server;
	private static Client client;
	
	@BeforeClass
	public static void setUp() throws Exception {
		server = new Server(PORT);
		client = new Client(PORT);
		server.start();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		server.stop();
	}
	
	@After
	public void cleanTheServersjobs() {
		Job.INSTANCE.loadJobs(new HashMap<String,String[]>());
	}
		
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyJobs() {
		client.jobs().toString();
	}
	
	@Test 
	public void testNewJob() {
		client.newJob("sweng", "01:30");
		assertEquals("[sweng]", client.jobs().toString());
		assertEquals("{inizio=01:30, fine=}", client.job("sweng").toString());
	}
	
	@Test
	public void testJobs() {
		client.newJob("sweng", "9:30");
		client.newJob("prova", "19:30");
		assertEquals("[sweng, prova]", client.jobs().toString());
	}
	
	@Test
	public void testEndJob() {
		client.newJob("sweng", "9:30");
		client.endJob("sweng", "12:30");
		assertEquals("{inizio=9:30, fine=12:30}", client.job("sweng").toString());
	}
	
	@Test
	public void testRunningsJobs() {
		client.newJob("sweng", "9:30");
		client.newJob("pranzo", "12:30");
		assertEquals("[sweng, pranzo]", client.runningJobs().toString());
	}
	
	@Test
	public void testActiveJobs() {
		client.newJob("pranzo", "12:30");
		client.endJob("pranzo", "13:00");
		client.newJob("youtube", "12:30");
		client.endJob("youtube", "13:30");
		assertEquals("[youtube, pranzo]", client.activeJobs("12:45").toString());
	}
}
