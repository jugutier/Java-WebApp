package ar.edu.itba.grupo2.domain.user;

import java.util.Date;
import java.util.List;

public interface UserRepo {
	
	public User get(final int id);
	
	public User getUserByEmail(final String email);
	
	public User save(final User user);
	
	public abstract User logIn(String email, String password);
	
	public abstract User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer);
	
	public List<User> getAll();

	public Object getLatestComments(User logged);
	

}
