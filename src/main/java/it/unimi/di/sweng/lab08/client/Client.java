package it.unimi.di.sweng.lab08.client;

import java.util.Map;
import java.util.Set;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


public class Client {

	private String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public Set<String> jobs() {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs", JobResource.class);
		if(jobs.getAllNames().isEmpty()) throw new IllegalArgumentException("There are no Jobs");
		return jobs.getAllNames();
	}

	public String job(final String string) {
		final GetJobResource jobs = ClientResource.create(serverUrl + "/j/job/" + string, GetJobResource.class);
		Map<String, String> info = jobs.getJobInfo();
		StringBuilder s = new StringBuilder(string + " = start:" + info.get("inizio"));
		if (!info.get("fine").isEmpty()) s.append(" end:" + info.get("fine"));
		return s.toString();		
	}
	
	public void newJob(final String job, final String begin) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job/");
		jobClient.addSegment(job);
		jobClient.addSegment("begin");
		jobClient.addSegment(begin);
		final PostJobWithBeginResource newJob = jobClient.wrap(PostJobWithBeginResource.class);
		newJob.post();
	}

	public void endJob(final String job, final String end) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job/");
		jobClient.addSegment(job);
		jobClient.addSegment("end");
		jobClient.addSegment(end);
		final PostJobWithBeginResource newJob = jobClient.wrap(PostJobWithBeginResource.class);
		newJob.post();
	}

	public static void main(String args[]) {

		if (args.length == 0) {
			System.err.println("Missing command, available commands: jobs|job|newJob|endJob");
			System.exit(-1);
		}

		final Client client = new Client(8080);
		final String verb = args[0];
		switch (verb) {

		case "jobs":
			try {
				//System.out.format("There are: ");
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
