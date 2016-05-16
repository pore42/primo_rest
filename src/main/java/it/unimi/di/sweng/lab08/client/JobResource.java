package it.unimi.di.sweng.lab08.client;

import java.util.Set;

import org.restlet.resource.Get;


public interface JobResource {
	@Get
	public Set<String> getAllNames();
}
