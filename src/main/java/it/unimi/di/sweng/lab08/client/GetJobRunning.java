package it.unimi.di.sweng.lab08.client;

import java.util.List;

import org.restlet.resource.Get;


public interface GetJobRunning  {
	@Get("json")
	public List<String> jobRunning();
}
