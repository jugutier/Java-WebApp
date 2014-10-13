package ar.edu.itba.grupo2.web.session;

import ar.edu.itba.grupo2.model.User;

public class UserManagerImpl2 implements UserManager {
	
	User user;
	
	public UserManagerImpl2() { }

	@Override
	public boolean existsUser() {
		return user != null;
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public void setUser(User loggedUser) {
		user = loggedUser;
	}

	@Override
	public void resetUser() {
		user = null;
	}

	@Override
	public User getUser() {
		return user;
	}

}
