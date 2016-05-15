package it.unimi.di.sweng.lab08.client;

import java.util.Set;
import java.util.Map;

import org.restlet.resource.ClientResource;


public class Client {

	private String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	private Map<String,String[]> allJobs() {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs", JobResource.class);
		return jobs.jobQuantities();
	}
	
	public Set<String> jobs() {
		final Map<String,String[]> allJobs = allJobs();
		return allJobs.keySet();
	}

}
