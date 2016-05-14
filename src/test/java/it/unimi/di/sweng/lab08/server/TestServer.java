package it.unimi.di.sweng.lab08.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.restlet.resource.ResourceException;

import it.unimi.di.sweng.lab08.example.mock.MockClient;
import it.unimi.di.sweng.lab08.model.Job;

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
	
	@Test
	public void testEmpty() throws ResourceException, IOException {
		Job.INSTANCE.loadJobs(new HashMap<String, String[]>());
		assertEquals("[]", mockClient.get("/j/jobs"));
	}
	
	@Test
	public void testJobs() throws ResourceException, IOException {
		final Map<String, String[]> jobs = new HashMap<String, String[]>();
		String[] time = {"13:30", ""};
		jobs.put("Bacon", time);
		jobs.put("Bread", time);
		Job.INSTANCE.loadJobs(jobs);
		assertEquals("[\"Bread\",\"Bacon\"]", mockClient.get("/j/jobs"));
	}
	
	@Test
	public void testGetJobInfo() throws ResourceException, IOException {
		final Map<String, String[]> jobs = new HashMap<String, String[]>();
		String[] time1 = {"16:30", ""};
		String[] time2 = {"20:30", ""};
		jobs.put("sport", time1);
		jobs.put("pizza", time2);
		Job.INSTANCE.loadJobs(jobs);
		assertEquals("16:30",mockClient.get("/j/job/sport"));
	}
	
}
