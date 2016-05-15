package it.unimi.di.sweng.lab08.example.client;

import org.restlet.resource.Post;
// questa interfaccia viene implementata dal server
public interface EatResource {

	@Post
	public void eat();

}
