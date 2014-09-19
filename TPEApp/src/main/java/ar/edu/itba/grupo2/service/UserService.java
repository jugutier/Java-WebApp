package ar.edu.itba.grupo2.service;

import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.RegisterErrorException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

public interface UserService {

	public abstract User logIn(String email, String password);

	public abstract User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer)
			throws RegisterErrorException;

	public abstract List<Comment> getCommentsByUser(User user);

}