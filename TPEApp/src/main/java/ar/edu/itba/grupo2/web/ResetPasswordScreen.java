package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserService;

@SuppressWarnings("serial")
public class ResetPasswordScreen extends BaseWebServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		
		req.setAttribute("stage", "getEmail");
		
		req.getRequestDispatcher("/WEB-INF/jsp/resetPassword.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
		UserService userService = UserService.getInstance();
		UserManagerDAO u = UserManagerPSQLImpl.getInstance();
		User user = null;
		
		String email = req.getParameter("email");
		
		if (email != null) {
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
