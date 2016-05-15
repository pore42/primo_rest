package it.unimi.di.sweng.lab08.client;

import java.util.Set;
import org.restlet.resource.ClientResource;


public class Client {

	private String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public Set<String> jobs() {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs", JobResource.class);
		return jobs.jobQuantities().keySet();
	}

	public String job(final String string) {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs/" + string, JobResource.class);
		return string + "=" + jobs.jobQuantities().get(string);
	}
	
	public void newJob(final String job, final String begin) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job/");
		jobClient.addSegment(job);
		jobClient.addSegment("/begin");
		jobClient.addSegment(begin);
		final NewJobResource newJob = jobClient.wrap(NewJobResource.class);
		newJob.add();
	}

	public void endJob(final String job, final String end) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job/");
		jobClient.addSegment(job);
		jobClient.addSegment("/end");
		jobClient.addSegment(end);
		final NewJobResource newJob = jobClient.wrap(NewJobResource.class);
		newJob.add();
	}

}
