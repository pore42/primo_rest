package it.unimi.di.sweng.lab08.example.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GreetResource extends ServerResource {

	@Get("json")
	public String greet() {
		final String name = getAttribute("name");
		return "Hi " + name +"!";
	}

}
