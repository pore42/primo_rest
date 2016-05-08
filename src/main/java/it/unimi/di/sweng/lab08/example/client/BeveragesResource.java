package it.unimi.di.sweng.lab08.example.client;

import java.util.List;

import org.restlet.resource.Get;

public interface BeveragesResource {

	@Get
	public List<String> beverages();

}
