package it.unimi.di.sweng.lab08.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class JobsApplication extends Application {
	
	@Override
    public Restlet createInboundRoot() {
        Router router = new Router( getContext() );
        
        router.attach( "/jobs", JobsResource.class );
        router.attach("/job/{name}", GetJobResource.class );
        router.attach("/job/{name}/begin/{begin}", PostJobWithBeginResource.class);
        router.attach("/job/{name}/end/{end}", PostJobWithEndResource.class);
        router.attach("/running", GetJobRunning.class);
        return router;
    }
}
