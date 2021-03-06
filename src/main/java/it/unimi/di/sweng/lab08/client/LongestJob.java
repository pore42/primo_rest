package it.unimi.di.sweng.lab08.client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class LongestJob extends AbstractStatistics {
	private static int PORT;
	private static String longestJob = null;
	
	public LongestJob(int port) {
		PORT = port;
	}
	@Override
	public String printStat() {
		String duration = Double.toString(compute());
		return String.format("The longest job is %s with a duration of %s hours\n", longestJob, duration);
	}

	private double compute(){
		super.pullInfo(PORT);
		Set<String> keys = data.keySet();
		Duration maxDuration = Duration.ZERO;
		
		for (String key : keys) {
			LocalTime begin = LocalTime.parse(data.get(key)[0]);
			if(!data.get(key)[1].isEmpty()) {
			LocalTime end = LocalTime.parse(data.get(key)[1]);
				if (maxDuration.compareTo(Duration.between(begin, end)) == -1) {
					maxDuration = Duration.between(begin,end);
					longestJob = key;
				}
			}
		}
		double duration = maxDuration.toMinutes();
		return round(toHours(duration), 1);
	}
}
