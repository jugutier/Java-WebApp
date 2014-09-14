package ar.edu.itba.it.paw.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.UserManager;

@SuppressWarnings("serial")
public class LogoutUser extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		UserManager userManager = new UserManager(req);
		
		if(userManager.existsUser()){
			userManager.resetUser(userManager.getEmail());
		}
		
		resp.sendRedirect(resp.encodeRedirectURL("welcome"));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
