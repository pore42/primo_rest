package it.unimi.di.sweng.lab08.example.client;

import org.restlet.resource.Post;

public interface DrinkResource {
	// stesso discorso fatto per EatResource
	@Post
	public void drink();

}
