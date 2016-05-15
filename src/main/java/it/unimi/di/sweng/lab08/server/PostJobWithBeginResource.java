package it.unimi.di.sweng.lab08.server;

import java.util.NoSuchElementException;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import it.unimi.di.sweng.lab08.model.Job;

public class PostJobWithBeginResource extends ServerResource{

	@Post
	public void post()
	{
		final String name = getAttribute("name");
		final String time = getAttribute("begin");
		try {
			Job.INSTANCE.setBegin(name, time);
		} catch (NoSuchElementException e)
		{
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e.getMessage());
		}
	}
	
}
