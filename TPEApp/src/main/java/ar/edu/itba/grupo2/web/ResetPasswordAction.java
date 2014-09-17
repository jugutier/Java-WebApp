package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.model.User;

@SuppressWarnings("serial")
public class ResetPasswordAction extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserManagerDAO u = UserManagerPSQLImpl.getInstance();
		User user = null;
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		String answer = req.getParameter("answer");
		
		if (email == null || password == null || passwordConfirm == null || answer == null) {
			resp.sendRedirect(resp.encodeRedirectURL("resetPassword"));
			return;
		}
		else {
			user = u.getUserByEmail(email);
			
			if (user != null && user.getSecretAnswer().compareTo(answer) == 0 && password.compareTo(passwordConfirm) == 0) {
				resp.sendRedirect(resp.encodeRedirectURL("filmList"));
				user.setPassword(password);
				u.saveUser(user);
				return;
			}
			else{
				resp.sendRedirect(resp.encodeRedirectURL("home"));
				return;
			}
		}
		
	}

}
