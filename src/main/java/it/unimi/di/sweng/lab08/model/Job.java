package it.unimi.di.sweng.lab08.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		Map<String, String> result = new HashMap<String, String>();
		result.put("inizio", time[0]);
		if(time[1] != "") result.put("fine", time[1]);
		else result.put("fine", "");
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
		JOB.clear();
		JOB.putAll(jobs);
	}

	private boolean checkHour(final String hour) {
		String[] splitted = hour.split(":");
		if (splitted.length==1)
			return false;
		if ((Integer.parseInt(splitted[0])>23 || Integer.parseInt(splitted[0])<0) || (Integer.parseInt(splitted[1])>59 || Integer.parseInt(splitted[0])<0))
			return false;
		return true;
	}

	private boolean isBeforeStart(final String job, final String hour) {
		String[] hours = JOB.get(job);
		if (!compareHour(hour, hours))
			return true;
		return false;
	}

	public List<String> jobRunning() {
		List<String> result = new ArrayList<String>();
		Iterator<Entry<String, String[]>> it = JOB.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			String[] tmp = entry.getValue();
			if (tmp[1] == "")
				result.add((String) entry.getKey());
		}
		return result;
	}

	public List<String> getJobActive (final String hour) {
		String newHour = hour.replace("%3A", ":");
		List<String> result = new ArrayList<String>();
		Iterator<Entry<String, String[]>> it = JOB.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			String[] tmp = entry.getValue();
			if (compareHour(newHour,tmp))
				result.add((String) entry.getKey());
		}
		return result;
	}

	private boolean compareHour(String hour, String[] map) {
		String[] splitted = hour.split(":");
		String[] start = map[0].split(":");
		String[] end = map[1].split(":");
		if (Integer.parseInt(start[0])<Integer.parseInt(splitted[0]) || (Integer.parseInt(start[0])==Integer.parseInt(splitted[0]) && Integer.parseInt(start[1])<=Integer.parseInt(splitted[1]))) {
			if (map[1].compareTo("")==0 || Integer.parseInt(splitted[0])<Integer.parseInt(end[0]))
				return true;
			else if (Integer.parseInt(splitted[0])==Integer.parseInt(end[0]) && Integer.parseInt(splitted[1])<=Integer.parseInt(end[1]))
				return true;
			else
				return false;
		}
		return false;
	}
}
