package it.unimi.di.sweng.lab08.example.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class TestMocks {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private final static int PORT = 8000;

	private static MockServer mockServer;
	private static MockClient mockClient;

	@BeforeClass
	public static void setUp() throws Exception {
		mockServer = new MockServer(PORT);
		mockClient = new MockClient(PORT);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		mockServer.stop();
	}

	@Test
	public void testGet() throws ResourceException, IOException {
		mockServer.setReply(Method.GET, "/a/nice/path", "the get reply");
		assertEquals("the get reply", mockClient.get("/a/nice/path"));
	}

	@Test
	public void testPost() throws ResourceException, IOException {
		mockServer.setReply(Method.POST, "/a/nice/path", "the post reply");
		assertEquals("the post reply", mockClient.post("/a/nice/path"));
	}

	@Test
	public void testNoSuchMethod() throws IOException {
		mockServer.setReply(Method.POST, "/another/nice/path", "the reply");
		try {
			mockClient.get("/another/nice/path");
			fail();
		} catch (ResourceException e) {
			assertEquals(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, e.getResponse().getStatus());
		}
	}

	@Test
	public void testNotFound() throws ResourceException, IOException {
		mockServer.setReply(Method.GET, "/a/nice/path", "the reply");
		try {
			mockClient.get("/a/wrong/path");
			fail();
		} catch (ResourceException e) {
			assertEquals(Status.CLIENT_ERROR_NOT_FOUND, e.getResponse().getStatus());
		}
	}

}
