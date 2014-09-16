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

import ar.edu.itba.grupo2.service.UserService;

public class RegisterUser extends HttpServlet {

	public void init() throws ServletException {
		super.init();

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
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date birthdate;
		try {
			birthdate = outputDateFormat.parse(req.getParameter("birthdate"));
			errors = UserService.getInstance().registerUser(email, password, passwordConfirm, name, lastname, birthdate,secretQuestion,secretAnswer);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.setAttribute("errors", errors);
		

	}

}
