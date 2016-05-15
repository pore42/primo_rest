package it.unimi.di.sweng.lab08.client;

import java.util.Map;

import org.restlet.resource.Get;

public interface JobResource {
	@Get
	public Map<String,String[]> jobQuantities();
}
