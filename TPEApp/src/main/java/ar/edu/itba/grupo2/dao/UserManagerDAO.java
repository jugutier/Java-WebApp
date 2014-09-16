package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

public interface UserManagerDAO {

	public User getUserById(final int id) throws ConnectionException;

	public User getUserByEmail(final String email) throws ConnectionException;

	public User saveUser(final User user) throws ConnectionException;
	
	public List<Comment> getCommentsByUser(final User user) throws ConnectionException;

}
