package it.unimi.di.sweng.lab08.server;

import java.util.List;
import java.util.NoSuchElementException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.model.Job;

public class GetJobRunning extends ServerResource {
	@Get("json")
	public List<String> jobRunning() {
		try {
			return Job.INSTANCE.jobRunning();
		} catch (NoSuchElementException e ){
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e.getMessage());
		}	
		return null;
	}
}
