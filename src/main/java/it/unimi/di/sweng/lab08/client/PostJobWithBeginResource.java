package it.unimi.di.sweng.lab08.client;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public interface PostJobWithBeginResource {

	@Post
	public Representation post();
}
