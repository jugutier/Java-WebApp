package ar.edu.itba.it.paw.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.UserManager;

@SuppressWarnings("serial")
public class AuthenticateUser extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String username = req.getParameter(UserManager.USERNAME);
		String password = req.getParameter(UserManager.PASSWORD);
		UserManager userManager = new UserManager(req);
		
		boolean authenticated = API.authenticateUser(username, password);
		
		if (authenticated){
			userManager.setUser(username, password);
			resp.sendRedirect(resp.encodeRedirectURL("listHotels"));
		}
		else{
			resp.sendRedirect(resp.encodeRedirectURL("welcome?status=auth_fail"));
		}
	}
}
