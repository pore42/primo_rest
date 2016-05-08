package it.unimi.di.sweng.lab08.example.client;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Client {

	private final String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public Set<Entry<String, Integer>> foods() {
		final FoodsResource foods = ClientResource.create(serverUrl + "/ed/foods", FoodsResource.class);
		return foods.foodQuantities().entrySet();
	}

	public void eat(final String food) {
		final ClientResource eatClient = new ClientResource(serverUrl + "/ed/eat");
		eatClient.addSegment(food);
		final EatResource eat = eatClient.wrap(EatResource.class);
		eat.eat();
	}

	public List<String> beverages() {
		final BeveragesResource beverages = ClientResource.create(serverUrl + "/ed/beverages", BeveragesResource.class);
		return beverages.beverages();
	}

	public void drink(final String beverage) {
		final ClientResource cr = new ClientResource(serverUrl + "/ed/drink/");
		cr.addSegment(beverage);
		final DrinkResource drink = cr.wrap(DrinkResource.class);
		drink.drink();
	}

	public static void main(String args[]) {

		if (args.length == 0) {
			System.err.println("Missing command, available commands: foods|eat FOOD|beverages|drink BEVERAGE");
			System.exit(-1);
		}

		final Client client = new Client(8080);
		final String verb = args[0];
		switch (verb) {

		case "foods":
			try {
				for (Entry<String, Integer> food : client.foods())
					System.out.format("There are %d items of %s\n", food.getValue(), food.getKey());
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "eat":
			try {
				client.eat(args[1]);
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "beverages":
			try {
				System.out.println("Beverages: " + client.beverages());
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "drink":
			try {
				client.drink(args[1]);
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		default:
			System.err.println("Unrecognized command, available commands: foods|eat FOOD|beverages|drink BEVERAGE");
			break;
		}

	}

}
