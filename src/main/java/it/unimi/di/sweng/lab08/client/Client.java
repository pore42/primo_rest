package it.unimi.di.sweng.lab08.client;

import java.util.Set;
import java.util.Map.Entry;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


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
		final PostJobWithBeginResource newJob = jobClient.wrap(PostJobWithBeginResource.class);
		newJob.post();
	}

	public void endJob(final String job, final String end) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job/");
		jobClient.addSegment(job);
		jobClient.addSegment("/end");
		jobClient.addSegment(end);
		final PostJobWithBeginResource newJob = jobClient.wrap(PostJobWithBeginResource.class);
		newJob.post();
	}

	public static void main(String args[]) {

		if (args.length == 0) {
			System.err.println("Missing command, available commands: jobs|job|newJob|endJob JOBS");
			System.exit(-1);
		}

		final Client client = new Client(8080);
		final String verb = args[0];
		switch (verb) {

		case "jobs":
			try {
				System.out.format("There are: ");
				for (String job : client.jobs())
					System.out.format("%s ", job);
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "newJob":
			try {
				client.newJob(args[1], args[2]);
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "job":
			try {
				System.out.println(client.job(args[1]));
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		case "endJob":
			try {
				client.endJob(args[1], args[2]);
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;

		default:
			System.err.println("Unrecognized command, available commands: foods|eat FOOD|beverages|drink BEVERAGE");
			break;
		}
	}
}
