package ar.edu.itba.grupo2.service.impl;

import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserService;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

public class UserServiceImpl implements UserService {

	private static UserService user_service;

	private UserServiceImpl() {
	}

	public static UserService getInstance() {
		if (user_service == null) {
			user_service = new UserServiceImpl();
		}

		return user_service;
	}

	@Override
	public User logIn(String email, String password) {
		User loggedUser = null;

		if (email == null || password == null) {
			return loggedUser;
		}
		loggedUser = UserManagerPSQLImpl.getInstance().getUserByEmail(email);
		if (loggedUser != null && !loggedUser.getPassword().equals(password)) {
			loggedUser = null;
		}
		return loggedUser;
	}

	@Override
	public User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer) {
		User newUser;
		if (ValidationUtilities.paramEmpty(name)
				|| ValidationUtilities.paramEmpty(lastname)
				|| ValidationUtilities.paramEmpty(secretQuestion)
				|| ValidationUtilities.paramEmpty(secretAnswer)
				|| ValidationUtilities.paramEmpty(password)
				|| !(password.equals(passwordConfirm))
				|| (birthdate == null)
				|| ValidationUtilities.paramEmpty(email)
				|| !ValidationUtilities.isEmail(email)
				|| (UserManagerPSQLImpl.getInstance().getUserByEmail(email) != null)) {
			throw new IllegalArgumentException();
		}
		newUser = new User.Builder().email(email).lastname(lastname).name(name)
				.password(password).birthdate(birthdate)
				.secretQuestion(secretQuestion).secretAnswer(secretAnswer)
				.build();

		UserManagerPSQLImpl.getInstance().saveUser(newUser);
		return newUser;
	}
	@Override
	public List<Comment> getCommentsByUser(final User user) {
		return UserManagerPSQLImpl.getInstance().getCommentsByUser(user);
	}

	public boolean existsUser(String email) {
		return UserManagerPSQLImpl.getInstance().getUserByEmail(email) != null;
	}

}