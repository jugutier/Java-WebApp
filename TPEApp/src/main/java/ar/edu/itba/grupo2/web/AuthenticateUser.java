package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserManager;
import ar.edu.itba.grupo2.service.UserService;

@SuppressWarnings("serial")
public class AuthenticateUser extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		User loggedUser = null;
		UserService userService = UserService.getInstance();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fromPage = req.getParameter("fromPage");
		
		if (fromPage == null) {
			fromPage = "home";
		}
	
		UserManager userManager = new UserManager(req);
		
		loggedUser = userService.logIn(email, password);
		
		if (loggedUser != null) {
			userManager.setUser(loggedUser);
			resp.sendRedirect(resp.encodeRedirectURL(fromPage.replace("auth_fail=wrongUser", "")));
		}
		else{
			char separator;
			if (fromPage.contains("?")) {
				separator = '&';
			}
			else {
				separator = '?';
			}
			resp.sendRedirect(resp.encodeRedirectURL(fromPage + separator + "auth_fail=wrongUser"));
		}
	}

}
