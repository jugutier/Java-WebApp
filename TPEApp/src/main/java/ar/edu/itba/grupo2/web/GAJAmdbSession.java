package ar.edu.itba.grupo2.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@SuppressWarnings("serial")
public class GAJAmdbSession extends WebSession {
	
	private EntityModel<User> model = null;

	public static GAJAmdbSession get() {
		return (GAJAmdbSession) Session.get();
	}

	public GAJAmdbSession(Request request) {
		super(request);
	}

	public User getLoggedInUser() {
		if (!isLoggedIn()) {
			return null;
		}
		return model.getObject();
	}

	public boolean authenticate(String email, String password, UserRepo users) {
		User user = users.logIn(email, password);
		if (user == null) {
			return false;
		}
		
		model = new EntityModel<User>(User.class, user);
		
		return true;
	}
	
	@Override
	public void detach() {
		super.detach();
		
		if (model != null)
			model.detach();
	}

	public boolean isLoggedIn() {
		return model != null;
	}

	public void logOut() {
        invalidate();
        clear();
        model = null;
	}
}
