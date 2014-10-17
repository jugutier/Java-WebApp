package ar.edu.itba.grupo2.service;

import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;

public interface UserService {

	public abstract User logIn(String email, String password);
	
	public abstract User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer);
	@Deprecated //TODO: make a list in User entity
	public abstract List<Comment> getCommentsByUser(User user);
	@Deprecated
	public abstract boolean existsUser(String email);
	
	public abstract boolean resetPasswordForEmail(String email, String password, String answer);
	@Deprecated//get desde el usuario
	public abstract String getSecretQuestionForEmail(String email);
	
}