package ar.edu.itba.grupo2.domain.user;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;

public class HibernateUserRepo extends HibernateBaseRepo<User> implements UserRepo{
	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User logIn(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer) {
		// TODO Auto-generated method stub
		return null;
	}



}
