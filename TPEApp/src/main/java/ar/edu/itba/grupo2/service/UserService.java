package ar.edu.itba.grupo2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.dao.exceptions.RegisterErrorException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

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

	public User logIn(String email, String password) {
		User loggedUser = null;
		
		if (email == null || password == null) {
			return loggedUser;
		}
		try {
			loggedUser = UserManagerPSQLImpl.getInstance().getUserByEmail(
					email);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		if (loggedUser != null && !loggedUser.getPassword().equals(password)) {
			loggedUser = null;
		}
		return loggedUser;
	}

	public User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname, Date birthdate, String secretQuestion, String secretAnswer) throws RegisterErrorException {
		User newUser;
		List<String> errors = new ArrayList<String>();
		if (password == "") {
			errors.add("NoPass");
		} else if (!password.equals(passwordConfirm)) {
			errors.add("NoCoincidence");
		}
		if (email == "") {
			errors.add("NoMail");
		} else if (!email
				.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$")) {
			errors.add("InvalidMail");
		}
			else if(UserManagerPSQLImpl.getInstance().getUserByEmail(email)!= null){
				errors.add("MailUsed");
			}
		if (name == "") {
			errors.add("NoName");
		}
		if (lastname == "") {
			errors.add("NoLastname");
		}
		if(birthdate == null){
			errors.add("NoDate");
		}
		if(secretQuestion == ""){
			errors.add("NoSQ");
		}
		if(secretAnswer == ""){
			errors.add("NoSA");
		}
		if (errors.size() == 0) {
			newUser = new User.Builder().email(email).lastname(lastname)
					.name(name).password(password).birthdate(birthdate).secretQuestion(secretQuestion).secretAnswer(secretAnswer).build();

			UserManagerPSQLImpl.getInstance().saveUser(newUser);
		}else{
			throw new RegisterErrorException(errors);
		}
		return newUser;
		
	}
	
	public List<Comment> getCommentsByUser(final User user) {
		return UserManagerPSQLImpl.getInstance().getCommentsByUser(user);
	}

}
