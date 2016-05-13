package it.unimi.di.sweng.lab08.example.client;

import java.util.Map;

import org.restlet.resource.Get;

public interface FoodsResource {

	//qui arriverà Map<String, String> corrispondente { 
	// Map<String,String[2]>
	@Get
	public Map<String,Integer> foodQuantities();

}
