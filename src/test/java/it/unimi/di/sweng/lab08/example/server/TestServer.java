package it.unimi.di.sweng.lab08.example.server;

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
import it.unimi.di.sweng.lab08.example.model.Beverage;
import it.unimi.di.sweng.lab08.example.model.Food;
import it.unimi.di.sweng.lab08.example.server.Server;

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
	public void testGreet() throws ResourceException, IOException {
		assertEquals("Hi friend!", mockClient.get("/g/greet/friend"));
	}
	/*
	@Test
	public void testFoods() throws ResourceException, IOException {
		final Map<String, Integer> foods = new HashMap<String, Integer>();
		foods.put("Bacon", 3);
		foods.put("Bread", 5);
		Food.INSTANCE.loadFoods(foods);
		assertEquals("{\"Bacon\":3,\"Bread\":5}", mockClient.get("/ed/foods"));
	}
	*/
	@Test
	public void testEat() throws ResourceException, IOException {
		final Map<String, Integer> foods = new HashMap<String, Integer>();
		foods.put("Bread", 1);
		Food.INSTANCE.loadFoods(foods);
		mockClient.post("/ed/eat/Bread");
	}
	@Test(expected = ResourceException.class)
	public void testCantEat() throws ResourceException, IOException {
		final Map<String, Integer> foods = new HashMap<String, Integer>();
		foods.put("Bread", 1);
		Food.INSTANCE.loadFoods(foods);
		mockClient.post("/ed/eat/Bacon");
	}

	@Test
	public void testBeverages() throws ResourceException, IOException {
		final Map<String, Integer> beverages = new HashMap<String, Integer>();
		beverages.put("Water", 3);
		beverages.put("Wine", 5);
		Beverage.INSTANCE.loadBeverages(beverages);
		assertEquals("[\"Water\",\"Wine\"]", mockClient.get("/ed/beverages"));
	}

}
