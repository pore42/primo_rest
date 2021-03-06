package it.unimi.di.sweng.lab08.example.mock;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;

public class MockServer {

	private Server server;

	public MockServer(final int port) throws Exception {
		server = new Server(Protocol.HTTP, port);
		server.start();
	}

	public void setReply(final Method method, final String path, final String reply) {
		server.setNext(new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				final Method requestMethod = request.getMethod();
				if (method != requestMethod) {
					response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
					return;
				}
				final String requestPath = request.getResourceRef().getPath();
				if (path.equals(requestPath))
					response.setEntity(reply, MediaType.APPLICATION_ALL_JSON);
				else
					response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
		});
	}

	public void stop() throws Exception {
		server.stop();
	}

}
