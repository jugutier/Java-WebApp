package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

public interface UserManagerDAO {

	public User getUserById(final int id);

	public User getUserByEmail(final String email);

	public User saveUser(final User user);
	
	public List<Comment> getCommentsByUser(final User user);

}
