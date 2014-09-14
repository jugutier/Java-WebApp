package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.ConnectionException;
import ar.edu.itba.grupo2.dao.exceptions.UserAlreadyExistsException;
import ar.edu.itba.grupo2.dao.exceptions.UserNotFoundException;
import ar.edu.itba.it.paw.model.User;

public interface UserManagerDAO {
	
	public User getUserById (final int id) throws ConnectionException;
	public List<User> getAllUsers ();
	public User getUserByEmail(final String email) throws ConnectionException;
	public User saveUser(final User user) throws ConnectionException;
}
