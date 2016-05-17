package it.unimi.di.sweng.lab08.client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class ShortestJob extends AbstractStatistics {

	private static int PORT;
	private static String job;

	public ShortestJob(int port) {
		PORT = port;
	}
	
	@Override
	public String printStat() {
		String duration = Double.toString(compute());
		return String.format("The shortest job is %s with a duration of %s hours\n", job, duration);
	}


	private double compute(){
		super.pullInfo(PORT);
		Set<String> keys = data.keySet();
		Duration minDuration = Duration.ZERO;
		for (String key : keys) {
			LocalTime begin = LocalTime.parse(data.get(key)[0]);
			if(!data.get(key)[1].isEmpty()) {
				LocalTime end = LocalTime.parse(data.get(key)[1]);
					if (minDuration.compareTo(Duration.between(begin, end)) == 1 || minDuration.isZero()) {
						minDuration = Duration.between(begin,end);
						job = key;
					}
				}
		}
		double duration = minDuration.toMinutes();
		return round(toHours(duration), 1);
	}

}
