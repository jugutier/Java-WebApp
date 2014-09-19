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
public class ResetPasswordScreen extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setAttribute("stage", "getEmail");
		
		req.getRequestDispatcher("/WEB-INF/jsp/resetPassword.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		UserManagerDAO u = UserManagerPSQLImpl.getInstance();
		User user = null;
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		String answer = req.getParameter("answer");
		
		if (email != null) {
			if (answer != null)
			user = u.getUserByEmail(email);
			req.setAttribute("email", email);
			req.setAttribute("secretQuestion", user.getSecretQuestion());
			req.setAttribute("stage", "question");
		}
		else {
			doGet(req, resp);
		}
		
		req.getRequestDispatcher("/WEB-INF/jsp/resetPassword.jsp").forward(req, resp);
	}
}
