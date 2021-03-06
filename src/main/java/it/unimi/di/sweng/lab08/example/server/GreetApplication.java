package it.unimi.di.sweng.lab08.example.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class GreetApplication extends Application {

	@Override
    public Restlet createInboundRoot() {
        Router router = new Router( getContext() );
        
        router.attach( "/greet/{name}", GreetResource.class );

        return router;
    }

}
