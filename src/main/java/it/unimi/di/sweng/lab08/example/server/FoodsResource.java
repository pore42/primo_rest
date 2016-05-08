package it.unimi.di.sweng.lab08.example.server;

import java.util.Map;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.example.model.Food;

public class FoodsResource extends ServerResource {

	@Get("json")
	public Map<String, Integer> foodQuantities() {
		return Food.INSTANCE.foodQuantities();
	}

}
