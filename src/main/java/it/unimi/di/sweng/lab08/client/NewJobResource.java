package it.unimi.di.sweng.lab08.client;

import org.restlet.resource.Post;

public interface NewJobResource {

	@Post
	public void add();
}
