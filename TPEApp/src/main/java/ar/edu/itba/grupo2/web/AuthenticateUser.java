package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.service.UserManager;
import ar.edu.itba.grupo2.service.UserService;

@SuppressWarnings("serial")
public class AuthenticateUser extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		UserService userService = UserService.getInstance();
		String email = req.getParameter(UserManager.EMAIL);
		String password = req.getParameter(UserManager.PASSWORD);
		System.out.println(email);
		System.out.println(password);
		UserManager userManager = new UserManager(req);
		
		boolean authenticated = userService.logIn(email, password);
		
		if (authenticated){
			userManager.setUser(email, password);
			resp.sendRedirect(resp.encodeRedirectURL("home"));
		}
		else{
			resp.sendRedirect(resp.encodeRedirectURL("filmList"));
		}
	}

}
