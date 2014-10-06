package ar.edu.itba.grupo2.web.session;

import ar.edu.itba.grupo2.model.User;

public interface UserManager {

	public abstract boolean existsUser();

	public abstract String getName();

	public abstract void setUser(User loggedUser);

	public abstract void resetUser();
	
	public abstract User getUser();

}