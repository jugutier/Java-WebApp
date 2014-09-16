package ar.edu.itba.grupo2.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.grupo2.model.User;

public class UserManager {
	public static String USER = "user";
	private HttpServletRequest request;
	
	public UserManager(HttpServletRequest request) {
		this.request = request;
	}
	
	public boolean existsUser() {
		HttpSession session = request.getSession(false);
		return session != null && (session.getAttribute(USER) != null);
	}
	
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
	
	public void setUser(User loggedUser) {
		HttpSession session = request.getSession();
		session.setAttribute(USER, loggedUser);
	
	}
	
	public void resetUser(String username) {
		HttpSession session = request.getSession(false);
		
		if (session != null){
			session.setAttribute(USER, null);
		}
	}

}
