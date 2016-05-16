package it.unimi.di.sweng.lab08.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
		String newhour = hour.replace("%3A", ":");
		if (JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is already created.");
		if (!checkHour(newhour)) throw new IllegalArgumentException("Illegal hour");
		String[] time = {newhour, ""}; 
		JOB.put(job, time);
	}
	
	public synchronized void setEnd(final String job, final String hour) {
		String newHour = hour.replace("%3A", ":");
		if (!JOB.containsKey(job)) throw new NoSuchElementException("The job " + job + " is not already created.");
		if (!checkHour(newHour)) throw new IllegalArgumentException("Illegal hour");
		if (isBeforeStart(job, newHour)) throw new IllegalArgumentException("The job must end after his start");
		String[] time = JOB.get(job);
		time[1] = newHour;
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
		if (Integer.parseInt(hours[0].split(":")[0]) > Integer.parseInt(hour.split(":")[0]))
			return true;
		else if(Integer.parseInt(hours[0].split(":")[0]) == Integer.parseInt(hour.split(":")[0]) && Integer.parseInt(hours[0].split(":")[1]) > Integer.parseInt(hour.split(":")[1]))
			return true;
		return false;
	}
	
	public List<String> jobRunning() {
		List<String> result = new ArrayList<String>();
		Iterator it = JOB.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String[] tmp = (String[]) entry.getValue();
			if (tmp[1] == "")
				result.add((String) entry.getKey());
		}
		return result;
	}
	
	public List<String> getJobActive (final String hour) {
		String newHour = hour.replace("%3A", ":");
		List<String> result = new ArrayList<String>();
		Iterator it = JOB.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String[] tmp = (String[]) entry.getValue();
			if (tmp[0].compareTo(newHour)<=0 && (tmp[1].compareTo("")==0 || tmp[1].compareTo(newHour)>=0))
				result.add((String) entry.getKey());
		}
		return result;
	}
}
