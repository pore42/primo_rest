package it.unimi.di.sweng.lab08.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStatistics implements StatisticsStrategy {
	Map<String,String[]> data = new HashMap<String,String[]>();
	
	@Override
	public void pullInfo(final int port) {
		Client client = new Client(port);
		List<String> jobs = client.jobs();
		String[] time = new String[2];
		for (String job : jobs) {
			Map<String,String> info = client.job(job);
			
			String s = info.get("inizio");
			time[0] = s;
			s = info.get("fine");
			time[1] = s;
			data.put(job, time);
			
		}
	}

	@Override
	public abstract String printStat();

}
