package it.unimi.di.sweng.lab08.client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class AverageJobs extends AbstractStatistics{
	private static int PORT;

	public AverageJobs(int port) {
		PORT = port;
	}

	@Override
	public String printStat() {
		String duration = Double.toString(compute());
		return String.format("The average duration of a job is of %s h\n", duration);
	}


	private double compute(){
		super.pullInfo(PORT);
		Set<String> keys = data.keySet();
		Duration totalDuration = Duration.ZERO;
		
		for (String key : keys) {
			LocalTime begin = LocalTime.parse(data.get(key)[0]);
			
			if(!data.get(key)[1].isEmpty()) {
				LocalTime end = LocalTime.parse(data.get(key)[1]);
				totalDuration = Duration.between(begin,end);
			}
		}
		double duration = totalDuration.toMinutes() / data.size();
		return round(toHours(duration), 2);
	}
	
	
}
