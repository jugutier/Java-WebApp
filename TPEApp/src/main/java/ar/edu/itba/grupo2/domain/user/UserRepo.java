package ar.edu.itba.grupo2.domain.user;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

public interface UserRepo {
	@Transactional
	public User get(final int id);
	@Transactional
	public User getUserByEmail(final String email);
	@Transactional
	public User save(final User user);
	
	public abstract User logIn(String email, String password);
	
	public abstract User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer);

}
