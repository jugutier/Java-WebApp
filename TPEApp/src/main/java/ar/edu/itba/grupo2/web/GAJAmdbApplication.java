package ar.edu.itba.grupo2.web;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.parse4j.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.web.common.HibernateRequestCycleListener;

@Component
public class GAJAmdbApplication extends WebApplication {
	
	private static final String APP_ID = "bH0IAo6UbBCuaXVVFZQl62vgaOs6l4vBRmDmyZMl";
	private static final String APP_REST_API_ID = "EwQWDYrnMcTqQ7MJOxnl3l9aA12bBHOPKXp3AIFY";
	
	private final SessionFactory sessionFactory;
	
	@Autowired
	public GAJAmdbApplication(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
    @Override
    public Class<? extends Page> getHomePage() {  
      return HomePage.class;
    }
    
    @Override
	protected void init() {
		super.init();
		Parse.initialize(APP_ID, APP_REST_API_ID);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
		
		getApplicationSettings().setPageExpiredErrorPage(ExpiredPage.class);
	}
    
    @Override
	public Session newSession(Request request, Response response) {
		return new GAJAmdbSession(request);
	}
    
}
