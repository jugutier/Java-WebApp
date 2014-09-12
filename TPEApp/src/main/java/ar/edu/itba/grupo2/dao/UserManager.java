package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.it.paw.model.User;

public interface UserManager {
	
	public User getUserById (final int id);
	public boolean existsUser(final User user);
	public List<User> getAllUsers ();
	public User getUserByEmail(final User user);
	public boolean authenticateUser (final User user);
	public User registerUser(final User user);

}
