package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.service.UserService;
import ar.edu.itba.grupo2.service.impl.UserServiceImpl;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

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
		UserService userService = UserServiceImpl.getInstance();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		String answer = req.getParameter("answer");
		String stage = req.getParameter("stage");
		
		List<String> errors = new ArrayList<String>();
		
		if (stage != null && stage.compareTo("getEmail") == 0) {
			errors.addAll(validateEmailStage(email));
			
			if (errors.isEmpty()) {
				// Display Secret Question
				req.setAttribute("email", email);
				req.setAttribute("secretQuestion", userService.getSecretQuestionForEmail(email));
				req.setAttribute("stage", "question");
			}
		}
		else if (stage != null && stage.compareTo("question") == 0){
			errors.addAll(validateAnswerStage(email, answer, password, passwordConfirm));
			
			if (errors.isEmpty()) {
				resp.sendRedirect(resp.encodeRedirectURL("home"));
				return;
			}
		}
		else {
			doGet(req, resp);
		}
		
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			doGet(req, resp);
		}
		else {
			req.getRequestDispatcher("/WEB-INF/jsp/resetPassword.jsp").forward(req, resp);
		}
	}
	
	private List<String> validateEmailStage(String email) {
		List<String> errors = new ArrayList<String>();
		UserService userService = UserServiceImpl.getInstance();
		
		if (ValidationUtilities.paramEmpty(email)) {
			errors.add("noMail");
		}
		else if (!ValidationUtilities.isEmail(email)) {
			errors.add("invalidMail");
		}
		else if (!userService.existsUser(email)){
			errors.add("wrongMail");
		}
		
		return errors;
	}
	
	private List<String> validateAnswerStage(String email, String answer,
			String password, String passwordConfirm) {
		List<String> errors = new ArrayList<String>();
		UserService userService = UserServiceImpl.getInstance();
		
		errors.addAll(validateEmailStage(email));
		
		if (ValidationUtilities.paramEmpty(answer)) {
			errors.add("noAnswer");
		}
		if (ValidationUtilities.paramEmpty(password)) {
			errors.add("noPassword");
		}
		if (ValidationUtilities.paramEmpty(passwordConfirm)) {
			errors.add("noPasswordConfirm");
		}
		if (password.compareTo(passwordConfirm) != 0) {
			errors.add("noCoincidence");
		}
		if (!userService.resetPasswordForEmail(email, passwordConfirm, answer)) {
			errors.add("wrongAnswer");
		}
		
		return errors;
	}
}
