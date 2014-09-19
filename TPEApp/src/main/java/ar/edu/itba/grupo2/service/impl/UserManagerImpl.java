package ar.edu.itba.grupo2.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserManager;

public class UserManagerImpl implements UserManager {
	public static String USER = "user";
	private HttpServletRequest request;
	
	public UserManagerImpl(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public boolean existsUser() {
		HttpSession session = request.getSession(false);
		return session != null && (session.getAttribute(USER) != null);
	}

	@Override
	public String getName() {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		User userAttribute = (User)session.getAttribute(USER);
		if (userAttribute != null) {
			return userAttribute.getName();
		}
		else{
			return null;
		}
	}

	@Override
	public void setUser(User loggedUser) {
		HttpSession session = request.getSession();
		session.setAttribute(USER, loggedUser);
	
	}

	@Override
	public void resetUser() {
		HttpSession session = request.getSession(false);
		
		if (session != null){
			session.setAttribute(USER, null);
		}
	}
	
	@Override
	public User getUser() {
		return (User)request.getSession().getAttribute(USER);
	}

}
