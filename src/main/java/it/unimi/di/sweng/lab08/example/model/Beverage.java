package it.unimi.di.sweng.lab08.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/*
 * A singleton implemented using an ENUM as suggested in "Effective Java", by Joshua Bloch (see https://goo.gl/5NnAn0) 
 */
public enum Beverage {
	INSTANCE;

	private final static Map<String, Integer> BEVERAGE = new HashMap<String,Integer>();

	public synchronized List<String> availableBeverages() {
		final List<String> beverages = new ArrayList<String>();
		for (Entry<String, Integer> entry : BEVERAGE.entrySet())
			if (entry.getValue() > 0) beverages.add(entry.getKey());
		return beverages;
	}

	public synchronized void drink(final String beverage) {
		if (!BEVERAGE.containsKey(beverage)) throw new NoSuchElementException("The beverage " + beverage + " is not available.");
		final int amount = BEVERAGE.get(beverage);
		if (amount == 0) throw new NoSuchElementException("The beverage " + beverage + " is out of stock.");
		BEVERAGE.put(beverage, amount - 1);
	}

	public synchronized void loadBeverages(Map<String, Integer> beverages) {
		BEVERAGE.clear();
		BEVERAGE.putAll(beverages);
	}

}
