package it.unimi.di.sweng.lab08.client;

import java.util.Map;

public abstract class StatisticStrategy
{
	private Map<String, String> datiAgenda;
	
	public StatisticStrategy(Map<String, String> dati)
	{
		datiAgenda=dati;
	}
	
	public abstract int calcula(String tipocalcolo);

}
