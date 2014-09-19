package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.exceptions.RegisterErrorException;
import ar.edu.itba.grupo2.service.impl.UserServiceImpl;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

@SuppressWarnings("serial")
public class RegisterScreen extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String email = (String) req.getParameter("email");
		String name = (String) req.getParameter("name");
		String lastname = (String) req.getParameter("lastname");
		String password = (String) req.getParameter("password");
		String passwordConfirm = (String) req.getParameter("passwordConfirm");
		String secretQuestion = (String) req.getParameter("secretQuestion");
		String secretAnswer = (String) req.getParameter("secretAnswer");
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = null;
		try {
			birthdate = outputDateFormat.parse(req.getParameter("birthdate"));
		} catch (ParseException e) {
			errors.add("WrongDate");
		} finally {
			errors.addAll(validate(email, name, lastname, password,
					passwordConfirm, secretQuestion, secretAnswer, birthdate));
			if(errors.size()!=0){
			req.setAttribute("errors", errors);
			this.doGet(req, resp);
			}
				UserServiceImpl.getInstance().registerUser(email, password,
						passwordConfirm, name, lastname, birthdate,
						secretQuestion, secretAnswer);
				resp.sendRedirect("home");
			}
	}

	private List<String> validate(String email, String name, String lastname,
			String password, String passwordConfirm, String secretQuestion,
			String secretAnswer, Date birthdate) {
		List<String> errors = new ArrayList<String>();
		if (ValidationUtilities.paramEmpty(email)) {
			errors.add("NoMail");
		} else if (ValidationUtilities.isEmail(email)) {
			errors.add("InvalidMail");
		}
		if (ValidationUtilities.paramEmpty(name)) {
			errors.add("NoName");
		}
		if (ValidationUtilities.paramEmpty(lastname)) {
			errors.add("NoLastname");
		}
		if (ValidationUtilities.paramEmpty(password)) {
			errors.add("NoPass");
		} else if (!password.equals(passwordConfirm)) {
			errors.add("NoCoincidence");
		}
		if (ValidationUtilities.paramEmpty(secretQuestion)) {
			errors.add("NoSQ");
		}
		if (ValidationUtilities.paramEmpty(secretAnswer)) {
			errors.add("NoSA");
		}
		if (birthdate == null) {
			errors.add("NoDate");
		}
		if(UserServiceImpl.getInstance().existsUser(email)){
			errors.add("MailUsed");
		}
		

		return errors;

	}

}
