package ar.edu.itba.grupo2.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class GAJAmdbApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {  
      return HomePage.class;
    }	
}
