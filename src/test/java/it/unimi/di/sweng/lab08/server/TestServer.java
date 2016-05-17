package it.unimi.di.sweng.lab08.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
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
	
	@Before
	public void emptyJobs() {
		final Map<String, String[]> jobs = new HashMap<String, String[]>();
		Job.INSTANCE.loadJobs(jobs);
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
		assertEquals("{\"fine\":\"\",\"inizio\":\"16:30\"}",mockClient.get("/j/job/sport"));
	}
	
	@Test
	public void testGetAllJobInfo() throws ResourceException, IOException {
		final Map<String,String[]> jobs = new HashMap<String,String[]>();
		String[] time = {"10:30", "12:30"};
		jobs.put("partita", time);
		Job.INSTANCE.loadJobs(jobs);
		assertEquals("{\"fine\":\"12:30\",\"inizio\":\"10:30\"}",mockClient.get("/j/job/partita"));
	}
	
	@Test (expected = ResourceException.class)
	public void testGetJobInfoException() throws ResourceException, IOException {
		mockClient.get("/j/job/compiti");
	}
	
	@Test
	public void testInsertBeginOfAJob() throws ResourceException, IOException {
		mockClient.post("/j/job/scuola/begin/13:30");
		assertEquals("{\"fine\":\"\",\"inizio\":\"13:30\"}", mockClient.get("/j/job/scuola"));
	}
	
	@Test (expected = ResourceException.class)
	public void testInsertThatFailBecauseJobAlreadyInsert() throws ResourceException, IOException {
		mockClient.post("/j/job/scuola/begin/13:30");
		mockClient.post("/j/job/scuola/begin/13:30");
	}
	
	@Test
	public void testInsertEndOfAJob() throws ResourceException, IOException {
		mockClient.post("/j/job/calcio/begin/13:30");
		mockClient.post("/j/job/calcio/end/15:30");
	}
	
	@Test (expected = ResourceException.class)
	public void testInsertEndOfAJobWithNoBegin() throws ResourceException, IOException {
		mockClient.post("/j/job/calcio/end/15:30");
	}
	
	@Test (expected = ResourceException.class)
	public void testWrongHour() throws ResourceException, IOException {
		mockClient.post("/j/job/mare/begin/18:36");
		mockClient.post("/j/job/festa/begin/25:12");
	}
	
	@Test (expected = ResourceException.class)
	public void testWrongHourFormat() throws ResourceException, IOException {
		mockClient.post("/j/job/festa/begin/25.12");
	}
	
	@Test (expected = ResourceException.class)
	public void testHourBefore() throws ResourceException, IOException {
		mockClient.post("/j/job/studio/begin/09:45");
		mockClient.post("/j/job/studio/end/9:30");
		mockClient.post("/j/job/test/begin/9:30");
		mockClient.post("/j/job/test/end/09:30");
	}
	
	@Test
	public void testRunning() throws ResourceException, IOException {
		mockClient.post("/j/job/mare/begin/18:36");
		mockClient.post("/j/job/cena/begin/19:20");
		assertEquals("[\"mare\",\"cena\"]",mockClient.get("/j/running"));
	}
	
	@Test
	public void testAnythingRunning() throws ResourceException, IOException {
		assertEquals("[]",mockClient.get("/j/running"));
		mockClient.post("/j/job/mare/begin/18:36");
		mockClient.post("/j/job/mare/end/19:20");
		assertEquals("[]",mockClient.get("/j/running"));
	}
	
	@Test
	public void testActive() throws ResourceException, IOException {
		mockClient.post("/j/job/pranzo/begin/12:30");
		mockClient.post("/j/job/studio/begin/14:15");
		assertEquals("[\"pranzo\"]", mockClient.get("/j/active/12:35"));
	}
	
	@Test
	public void testAnythingActive() throws ResourceException, IOException {
		mockClient.post("/j/job/calcio/begin/14:15");
		assertEquals("[]", mockClient.get("/j/active/12:30"));
	}
	
	@Test (expected = ResourceException.class)
	public void testNegativeNumbers() throws ResourceException, IOException {
		mockClient.post("/j/job/nome/begin/-12:-45");
	}
	
	@Test
	public void testFinale() throws ResourceException, IOException {
		assertEquals("[]", mockClient.get("/j/jobs"));
		mockClient.post("/j/job/sweng/begin/9:30");
		assertEquals("[\"sweng\"]", mockClient.get("/j/jobs"));
		assertEquals("{\"fine\":\"\",\"inizio\":\"9:30\"}", mockClient.get("/j/job/sweng"));
		assertEquals("[\"sweng\"]", mockClient.get("/j/running"));
		mockClient.post("/j/job/sweng/end/12%3A30");
		assertEquals("{\"fine\":\"12:30\",\"inizio\":\"9:30\"}", mockClient.get("/j/job/sweng"));
	}
	
	@Test
	public void testAct() throws ResourceException, IOException {
		mockClient.post("/j/job/sweng/begin/9:30");
		mockClient.post("/j/job/sweng/end/12%3A30");
		assertEquals("[\"sweng\"]",mockClient.get("/j/active/09:34"));
	}
	
}
