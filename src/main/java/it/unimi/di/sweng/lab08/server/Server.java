package it.unimi.di.sweng.lab08.server;

import java.util.HashMap;
import java.util.Map;

import org.restlet.Component;
import org.restlet.data.Protocol;

import it.unimi.di.sweng.lab08.example.model.Food;
import it.unimi.di.sweng.lab08.example.server.EatAndDrinkApplication;
import it.unimi.di.sweng.lab08.example.server.GreetApplication;
import it.unimi.di.sweng.lab08.model.Job;

public class Server {

	private final Component component;

	public Server(final int port, final Map<String, String[]> jobs) {
		if (jobs != null)
			Job.INSTANCE.loadJobs(jobs);
		component = new Component();
		component.getServers().add(Protocol.HTTP, port);
		component.getDefaultHost().attach("/g", new GreetApplication());
		component.getDefaultHost().attach("/ed", new EatAndDrinkApplication());
		component.getDefaultHost().attach("/j", new JobsApplication());
	}

	public Server(final int port) {
		this(port, null);
	}

	public void start() throws Exception {
		component.start();
	}

	public void stop() throws Exception {
		component.stop();
	}

	public static void main(String[] args) throws Exception {

		final Map<String, String[]> jobs = new HashMap<String, String[]>();

		final Server server = new Server(8080, jobs);
		server.start();
	}
	
}
