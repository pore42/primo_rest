package it.unimi.di.sweng.lab08.client;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.restlet.data.Method;

import it.unimi.di.sweng.lab08.client.Client;
import it.unimi.di.sweng.lab08.example.mock.MockServer;

public class TestClient {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private final static int PORT = 8000;

	private static Client client;
	private static MockServer mockServer;

	@BeforeClass
	public static void setUp() throws Exception {
		client = new Client(PORT);
		mockServer = new MockServer(PORT);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		mockServer.stop();
	}

	@Test
	public void testJobs() {
		mockServer.setReply(Method.GET,                  
				            "/j/jobs",                 
				            "[\"University\", \"Basket\"]"); 
		assertEquals("[Basket, University]",client.jobs().toString());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyJobs() {
		mockServer.setReply(Method.GET,                  
				            "/j/jobs",                 
				            "[]"); 
		assertEquals("[]",client.jobs().toString());
	}
	
	@Test
	public void testNameJobs() {
		mockServer.setReply(Method.GET,                  
				            "/j/job/Basket",                 
				            "{\"fine\":\"13:30\",\"inizio\":\"12:30\"}");
		assertEquals("Basket = start:12:30 end:13:30",client.job("Basket").toString());
	}
	
	@Test
	public void testNameBeginJobs() {
		mockServer.setReply(Method.GET,                  
				            "/j/job/Basket",                                  
	                        "{\"fine\":\"\",\"inizio\":\"12:30\"}"); 
		assertEquals("Basket = start:12:30",client.job("Basket").toString());
	}
	
	
}
