package it.unimi.di.sweng.lab08.client;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


public class Client {

	private static final String NOT_FOUND = "Not Found (404) - The server has not found anything matching the request URI";
	private String serverUrl;

	public Client(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public List<String> jobs() {
		final JobResource jobs = ClientResource.create(serverUrl + "/j/jobs", JobResource.class);
		if(jobs.getAllNames().isEmpty()) throw new IllegalArgumentException("There are no Jobs");
		return new ArrayList<String>(jobs.getAllNames());
	}

	public Map<String,String> job(final String string) {
		final GetJobResource jobs = ClientResource.create(serverUrl + "/j/job/" + string, GetJobResource.class);
		Map<String, String> info;
		try {
			info = jobs.getJobInfo();
		} catch (Exception e) {
			if (e.getMessage().equals(NOT_FOUND));
			throw new IllegalArgumentException("There is no " + string + " JOB");
		}
		return info;		
	}
	
	public void newJob(final String job, final String begin) {
		final ClientResource jobClient = buildClient("begin",job, begin);
		final PostJobWithBeginResource newJob = jobClient.wrap(PostJobWithBeginResource.class);
		System.out.println(jobClient.getResponse().getAge());
		newJob.post();
	}

	public void endJob(final String job, final String end) {
		final ClientResource jobClient = buildClient("end",job, end);
		final PostJobWithEndResource newJob = jobClient.wrap(PostJobWithEndResource.class);
		newJob.post();
	}
	
	public List<String> runningJobs() {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/running");
		final GetJobRunning newJob = jobClient.wrap(GetJobRunning.class);
		return newJob.jobRunning();
	}
	
	public List<String> activeJobs(String hour) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/active");
		jobClient.addSegment(hour);
		final GetJobActive newJob = jobClient.wrap(GetJobActive.class);
		return newJob.jobActive();
	}
	
	private ClientResource buildClient(final String action, final String job, final String begin) {
		final ClientResource jobClient = new ClientResource(serverUrl + "/j/job");
		jobClient.addSegment(job);
		jobClient.addSegment(action);
		jobClient.addSegment(begin);
		return jobClient;
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
				StringBuilder s = new StringBuilder(args[1] + ": inizio=");
				Map<String,String> info = client.job(args[1]);
				s.append(info.get("inizio"));
				if(!info.get("fine").isEmpty()) s.append(", fine=" + info.get("fine"));
				System.out.println(s.toString());
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
		case "running":
			try {
				for(String jobs : client.runningJobs()){
					System.out.println(jobs);
				};
			} catch (ResourceException e) {
				System.err.println("Server returned error: " + e.getMessage());
			}
			break;
		case "active":
			try {
				for(String jobs : client.activeJobs(args[1])){
					System.out.println(jobs);
				};
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
