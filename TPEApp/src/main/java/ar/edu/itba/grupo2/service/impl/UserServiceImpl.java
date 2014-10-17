package ar.edu.itba.grupo2.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.itba.grupo2.dao.PSQLImpl.UserManagerPSQLImpl;
import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserManagerRepo;
import ar.edu.itba.grupo2.service.UserService;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

@Service
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

	@Override
	public boolean existsUser(String email) {
		return UserManagerPSQLImpl.getInstance().getUserByEmail(email) != null;
	}

	@Override
	public boolean resetPasswordForEmail(String email, String password,
			String answer) {
		UserManagerRepo userManager = UserManagerPSQLImpl.getInstance();
		User user = userManager.getUserByEmail(email);

		if (user == null) {
			return false;
		}

		if (user.getSecretAnswer().compareTo(answer) != 0) {
			return false;
		}

		user.setPassword(password);

		userManager.saveUser(user);

		return true;
	}

	@Override
	public String getSecretQuestionForEmail(String email) {
		UserManagerRepo userManager = UserManagerPSQLImpl.getInstance();
		User user = userManager.getUserByEmail(email);

		if (email == null || user == null) {
			return null;
		}

		return user.getSecretQuestion();
	}

}
