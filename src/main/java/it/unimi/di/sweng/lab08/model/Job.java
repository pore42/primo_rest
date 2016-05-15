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
	
	public synchronized Map<String, String> getJobInfo(final String job) {
		if (!JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is not already created.");
		String[] time = JOB.get(job);
		/*
		StringBuilder res = new StringBuilder();
		String[] time = JOB.get(job);
		if (time[1]=="")
			res.append("Inizio " + time[0]);
		else
			res.append("Inizio " + time[0]).append(", fine " + time[1]);
		return res.toString();
		*/
		Map<String, String> result = new HashMap<String, String>();
		result.put("inizio", time[0]);
		if(time[1] != "") {
			result.put("fine", time[1]);
		} else {
			result.put("fine", "");
		}
		return result;
	}
	
	public synchronized void setBegin(final String job, final String hour) {
		if (JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is already created.");
		if (!checkHour(hour)) throw new IllegalArgumentException("Illegal hour");
		String[] time = {hour, ""}; 
		JOB.put(job, time);
	}
	
	public synchronized void setEnd(final String job, final String hour) {
		if (!JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is not already created.");
		if (!checkHour(hour)) throw new IllegalArgumentException("Illegal hour");
		if (isBeforeStart(job, hour)) throw new IllegalArgumentException("The job must end after his start");
		String[] time = JOB.get(job);
		time[1] = hour;
		JOB.put(job, time);
	}
	
	public void loadJobs(Map<String, String[]> jobs) {
		// TODO Auto-generated method stub
		JOB.clear();
		JOB.putAll(jobs);
	}
	
	private boolean checkHour(final String hour) {
		String[] splitted = hour.split(":");
		if (splitted.length==1)
			return false;
		if (Integer.parseInt(splitted[0])>23 || Integer.parseInt(splitted[1])>59)
			return false;
		return true;
	}
	
	private boolean isBeforeStart(final String job, final String hour) {
		String[] hours = JOB.get(job);
		if (hours[0].compareTo(hour)>0)
			return true;
		return false;
	}
}
