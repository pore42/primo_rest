package it.unimi.di.sweng.lab08.server;

import java.util.Map;
import java.util.NoSuchElementException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.model.Job;

public class AddJobsResource extends ServerResource {
	
	@Get("json")
	public Map<String, String> getJobInfo() {
		final String job = getAttribute("name");
		//String tmp = Job.INSTANCE.getJobInfo(job);		
		return Job.INSTANCE.getJobInfo(job);
	}
	
}
