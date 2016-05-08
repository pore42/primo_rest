package it.unimi.di.sweng.lab08.example.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class EatAndDrinkApplication extends Application {

	@Override
    public Restlet createInboundRoot() {
        Router router = new Router( getContext() );
        
        router.attach( "/drink/{beverage}", DrinkResource.class );
        router.attach( "/beverages", BeveragesResource.class );
        
        router.attach( "/eat/{food}", EatResource.class );
        router.attach( "/foods", FoodsResource.class );
        
        return router;
    }

}
