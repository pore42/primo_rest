package it.unimi.di.sweng.lab08.example.server;

import java.util.HashMap;
import java.util.Map;

import org.restlet.Component;
import org.restlet.data.Protocol;

import it.unimi.di.sweng.lab08.example.model.Beverage;
import it.unimi.di.sweng.lab08.example.model.Food;

public class Server {
	private final Component component;

	public Server(final int port, final Map<String, Integer> foods, final Map<String, Integer> beverages) {
		if (foods != null)
			Food.INSTANCE.loadFoods(foods);
		if (beverages != null)
			Beverage.INSTANCE.loadBeverages(beverages);
		component = new Component();
		component.getServers().add(Protocol.HTTP, port);
		component.getDefaultHost().attach("/g", new GreetApplication());
		component.getDefaultHost().attach("/ed", new EatAndDrinkApplication());
	}

	public Server(final int port) {
		this(port, null, null);
	}

	public void start() throws Exception {
		component.start();
	}

	public void stop() throws Exception {
		component.stop();
	}

	public static void main(String[] args) throws Exception {

		final Map<String, Integer> foods = new HashMap<String, Integer>();
		foods.put("Bacon", 3);
		foods.put("Bread", 5);
		foods.put("Lettuce", 4);
		foods.put("Tomato", 6);

		final Map<String, Integer> beverages = new HashMap<String, Integer>();
		beverages.put("Water", 3);
		beverages.put("Wine", 5);
		beverages.put("Beer", 4);

		final Server server = new Server(8080, foods, beverages);
		server.start();
	}
}
