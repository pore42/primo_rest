package it.unimi.di.sweng.lab08.example.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.restlet.data.Method;

import it.unimi.di.sweng.lab08.example.client.Client;
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

	/*@Test
	public void testFoods() {
		// fase di impostazione del mockServer
		mockServer.setReply(Method.GET,                   // azione da simulare
				            "/ed/foods",                  // dove dovrebbero essere localizzati i dati
				            "{\"Bacon\":3\"Bread\":5}"); // i dati che dovrebbero essere presenti nel serer
		System.out.println(client.foods());
	}*/

}
