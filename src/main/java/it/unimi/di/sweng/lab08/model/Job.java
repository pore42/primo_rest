package it.unimi.di.sweng.lab08.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum Job {
	INSTANCE;

	private final static Map<String, String[]> JOB = new HashMap<String,String[]>();
	
	public synchronized Map<String,String[]> totalJobs() {
		return new HashMap<String,String[]>(JOB); // we return a copy
	}
	
	public synchronized String getJobInfo(final String job) {
		if (!JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is not already created.");
		StringBuilder res = new StringBuilder();
		String[] time = JOB.get(job);
		if (time[1]=="")
			res.append(time[0]);
		else
			res.append(time.toString());
		return res.toString();
	}
	
	public synchronized void setBegin(final String job, final String hour) {
		if (JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is already created.");
		String[] time = {hour, ""}; 
		JOB.put(job, time);
	}
	
	public synchronized void setEnd(final String job, final String hour) {
		if (!JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is not already created.");
		String[] time = JOB.get(job);
		time[1] = hour;
		JOB.put(job, time);
	}
	
	public void loadJobs(Map<String, String[]> jobs) {
		// TODO Auto-generated method stub
		JOB.clear();
		JOB.putAll(jobs);
	}
}
