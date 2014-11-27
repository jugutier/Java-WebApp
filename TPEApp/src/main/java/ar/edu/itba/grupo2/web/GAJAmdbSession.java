package ar.edu.itba.grupo2.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

public class GAJAmdbSession extends WebSession {
	
	private Integer userId = null;

	public static GAJAmdbSession get() {
		return (GAJAmdbSession) Session.get();
	}

	public GAJAmdbSession(Request request) {
		super(request);
	}

	public User getLoggedInUser(UserRepo users) {
		if (!isLoggedIn()) {
			return null;
		}
		return users.get(userId);
	}

	public boolean authenticate(String email, String password, UserRepo users) {
		User user = users.logIn(email, password);
		if (user == null) {
			return false;
		}
		
		this.userId = user.getId();
		
		return true;
	}

	public boolean isLoggedIn() {
		return userId != null;
	}

	public void logOut() {
        invalidate();
        clear();
        userId = null;
	}
}
