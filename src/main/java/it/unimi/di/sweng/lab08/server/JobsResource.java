package it.unimi.di.sweng.lab08.server;

import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.resource.Get;

public class JobsResource extends ServerResource {

	@Get("json")
	public Set<String> getAllNames()
	{
		Map<String, String[]> tmp = Job.INSTANCE.totalJobs();		
		return tmp.keySet();
	}
}
