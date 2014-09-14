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
		String email = req.getParameter(UserManager.EMAIL);
		String password = req.getParameter(UserManager.PASSWORD);
		System.out.println(email);
		System.out.println(password);
		UserManager userManager = new UserManager(req);
		
		boolean authenticated = API.authenticateUser(email, password);
		
		if (authenticated){
			userManager.setUser(email, password);
			req.getRequestDispatcher("/WEB-INF/jsp/filmList.jsp").forward(req, resp);
		}
		else{
			resp.sendRedirect(resp.encodeRedirectURL("welcome?status=auth_fail"));
		}
	}
}
