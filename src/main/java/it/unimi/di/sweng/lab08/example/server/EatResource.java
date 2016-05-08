package it.unimi.di.sweng.lab08.example.server;

import java.util.NoSuchElementException;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.example.model.Food;

public class EatResource extends ServerResource {

	@Post
	public void eat() {
		final String food = getAttribute("food");
		try {
			Food.INSTANCE.eat(food);
		} catch (NoSuchElementException e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e.getMessage());
		}
	}

}
