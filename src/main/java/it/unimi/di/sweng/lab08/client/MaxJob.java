package it.unimi.di.sweng.lab08.client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class MaxJob extends AbstractStatistics {
	private static int PORT;
	
	public MaxJob(int port) {
		PORT = port;
	}
	@Override
	public String printStat() {
		super.pullInfo(PORT);
		Set<String> keys = data.keySet();
		Duration maxDuration = Duration.ZERO;
		for (String key : keys) {
			LocalTime begin = LocalTime.parse(data.get(key)[0]);
			LocalTime end = LocalTime.parse(data.get(key)[1]);
				if (maxDuration.compareTo(Duration.between(begin, end)) == -1 ||
				    maxDuration == null) 
					maxDuration = Duration.between(begin, end);
		}
			
		return String.valueOf(maxDuration.toMinutes() / 60);
	}

}
