package it.unimi.di.sweng.lab08.example.client;

import org.restlet.resource.Post;

public interface EatResource {

	@Post
	public void eat();

}
