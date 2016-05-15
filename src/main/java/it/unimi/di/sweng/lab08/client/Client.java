package it.unimi.di.sweng.lab08.client;

import java.util.Set;
import java.util.Map.Entry;

import org.restlet.resource.ClientResource;


public class Client {

	private String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public Set<Entry<String,String[]>> jobs() {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs", JobResource.class);
		return jobs.jobQuantities().entrySet();
	}

}
