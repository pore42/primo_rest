package it.unimi.di.sweng.lab08.client;

import org.restlet.resource.Post;

public interface PostJobWithEndResource {

	@Post
	public void post();
}