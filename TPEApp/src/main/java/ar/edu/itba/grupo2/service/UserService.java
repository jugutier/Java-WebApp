package ar.edu.itba.grupo2.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.it.paw.model.User;

public class UserService {
	
	private static UserService user_service;
	

	private UserService() {
	}

	public static UserService getInstance() {
		if (user_service == null) {
			user_service = new UserService();
		}

		return user_service;
	}
	
	public boolean logIn(User user){
		User loggedUser=null;
		if(user.getEmail()==null || user.getPassword()==null){
			return false;
		}
		try {
			loggedUser = UserManagerPSQLImpl.getInstance().getUserByEmail(user.getEmail());
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		if (loggedUser==null){
			return false;
		} 
			return true;	
	}
	
	public List<String> registerUser(String email,String password,String passwordConfirm,String name,String lastname,Date birthdate){
		
		List<String> errors = new ArrayList<String>();
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
		return errors;
	}

}
