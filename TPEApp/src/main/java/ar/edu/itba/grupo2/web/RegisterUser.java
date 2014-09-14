package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.UserManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.UserAlreadyExistsException;
import ar.edu.itba.grupo2.dao.exceptions.UserNotFoundException;
import ar.edu.itba.it.paw.model.User;

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
		
		if (password == null) {
			errors.add("Debe ingresar una contraseña");
		} else if (!password.equals(passwordConfirm)) {
			errors.add("Las contraseñas no coinciden");
		}
		if (email == null) {
			errors.add("Debe ingeresar un email");
		} else if (!email
				.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$")) {
			errors.add("Email invalido");
		}
		if (name == null) {
			errors.add("Debe ingresar un nombre");
		}
		if (lastname == null) {
			errors.add("Debe ingresar un apellido");
		}
		if (errors.size() == 0) {
			User newUser = new User.Builder().email(email).lastname(lastname)
					.name(name).password(password).build();
		} else {
			req.setAttribute("errors", errors);
		}

	}

}
