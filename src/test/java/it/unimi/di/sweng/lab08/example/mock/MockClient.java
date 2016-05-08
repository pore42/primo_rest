package it.unimi.di.sweng.lab08.example.mock;

import java.io.IOException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class MockClient {

	private final String serverUrl;

	public MockClient(final int port) {
		this.serverUrl = "http://localhost:" + port;
	}

	public String get(final String path) throws ResourceException, IOException {
		final ClientResource clientResource = new ClientResource(serverUrl + path);
		return clientResource.get().getText();
	}

	public String post(final String path) throws ResourceException, IOException {
		final ClientResource clientResource = new ClientResource(serverUrl + path);
		return clientResource.post(null).getText();
	}

}
