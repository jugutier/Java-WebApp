package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.web.session.UserManager;
import ar.edu.itba.grupo2.web.session.UserManagerImpl;

@SuppressWarnings("serial")
public class Logout extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserManager userManager = new UserManagerImpl((HttpServletRequest)req);
		userManager.resetUser();
		
		resp.sendRedirect(resp.encodeRedirectURL("home"));
	}

}
