package it.unimi.di.sweng.lab08.client;

public interface StatisticsStrategy {
	public void pullInfo(final int port);
	public String printStat();
}
