package it.unimi.di.sweng.lab08.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/*
 * A singleton implemented using an ENUM as suggested in "Effective Java", by Joshua Bloch (see https://goo.gl/5NnAn0) 
 */
public enum Food {
	INSTANCE;

	private final static Map<String, Integer> FOOD = new HashMap<String,Integer>();

	public synchronized Map<String,Integer> foodQuantities() {
		return new HashMap<String,Integer>(FOOD); // we return a copy
	}
	
	public synchronized void eat(final String food) {
		if (!FOOD.containsKey(food)) throw new NoSuchElementException("The food " + food + " is not available.");
		final int amount = FOOD.get(food);
		if (amount == 0) throw new NoSuchElementException("The food " + food + " is out of stock.");
		FOOD.put(food, amount - 1);
	}

	public synchronized void loadFoods(Map<String, Integer> foods) {
		FOOD.clear();
		FOOD.putAll(foods);
	}
	
}
