package it.unimi.di.sweng.lab08.example.server;

import java.util.NoSuchElementException;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.example.model.Beverage;

public class DrinkResource extends ServerResource {

	@Post
	public void drink() {
		final String beverage = getAttribute("beverage");
		try {
			Beverage.INSTANCE.drink(beverage);
		} catch (NoSuchElementException e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e.getMessage());
		}
	}

}
