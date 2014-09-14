package ar.edu.itba.it.paw.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserManager {
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	private HttpServletRequest request;
	
	public UserManager(HttpServletRequest request) {
		this.request = request;
	}
	
	public boolean existsUser() {
		HttpSession session = request.getSession(false);
		return (session != null && ((session.getAttribute(EMAIL) != null && session.getAttribute(PASSWORD) != null) ||
				(request.getParameter(EMAIL) != null && request.getParameter(PASSWORD) != null)));
	}
	
	public String getEmail() {
		return getByID(EMAIL);
	}
	
	
	public String getPassword() {
		return getByID(PASSWORD);
	}
	
	public void setUser(final String email,final String password) {
		HttpSession session = request.getSession();
		session.setAttribute(EMAIL, email);
		session.setAttribute(PASSWORD, password);
	}
	
	public void resetUser(String username) {
		HttpSession session = request.getSession(false);
		
		if (session != null){
			session.setAttribute(EMAIL, null);
			session.setAttribute(PASSWORD, null);
		}
	}
	
	private String getByID(String id) {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return null;
		}
		String value = (String)session.getAttribute(id);
		if (value != null) {
			return value;
		} else {
			return request.getParameter(id);
		}
	}

}
