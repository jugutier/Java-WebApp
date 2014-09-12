package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.UserManager;
import ar.edu.itba.grupo2.dao.memory.UserManagerSQL;
import ar.edu.itba.it.paw.model.User;

public class RegisterUser extends HttpServlet {
	
	public void init() throws ServletException{
		super.init();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String email = (String) req.getParameter("email");
		String name = (String) req.getParameter("name");
		String surname= (String)req.getParameter("surname");
		String password= (String)req.getParameter("password");
		String passwordConfirm= (String)req.getParameter("passwordConfirm");
		if(!password.equals(passwordConfirm)){
			errors.add("Las contraseñas no coinciden");
		}
		if(email == null){
			errors.add("Tiene que ingresar un email para registrarse");
		}
		else if(!email.matches("(.*)@(.*).com")){
			errors.add("Email invalido");
		}
		if(errors.size()==0){
			User newUser=new User.Builder().email(email).surname(surname).name(name).password(password).build();
			UserManagerSQL.getInstance().registerUser(newUser);
		}
		
	}

}
