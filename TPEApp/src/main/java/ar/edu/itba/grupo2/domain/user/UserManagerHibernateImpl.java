package ar.edu.itba.grupo2.domain.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.grupo2.domain.baseRepo.HibernateBaseRepo;
import ar.edu.itba.grupo2.domain.dao.UserManagerDAO;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

public class UserManagerHibernateImpl extends HibernateBaseRepo<User> implements UserManagerDAO{
	@Autowired
	public UserManagerHibernateImpl(SessionFactory sessionFactory) {
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
	public List<Comment> getCommentsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}



}
