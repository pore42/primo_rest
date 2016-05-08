package it.unimi.di.sweng.lab08.example.server;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.example.model.Beverage;

public class BeveragesResource extends ServerResource {

	@Get("json")
	public List<String> beverages() {
		return Beverage.INSTANCE.availableBeverages();
	}

}
