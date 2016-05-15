package it.unimi.di.sweng.lab08.example.client;

import java.util.Map;

import org.restlet.resource.Get;

public interface FoodsResource {
	// Trasforma un formato json in Map<String,Integer>
	@Get
	public Map<String,Integer> foodQuantities();

}
