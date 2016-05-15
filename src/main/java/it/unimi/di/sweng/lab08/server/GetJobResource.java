package it.unimi.di.sweng.lab08.server;

import java.util.Map;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.model.Job;

public class GetJobResource extends ServerResource {
	
	@Get("json")
	public Map<String, String> getJobInfo() {
		final String job = getAttribute("name");
		//String tmp = Job.INSTANCE.getJobInfo(job);		
		return Job.INSTANCE.getJobInfo(job);
	}
	
}