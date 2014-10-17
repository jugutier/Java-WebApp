package ar.edu.itba.grupo2.domain.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;

public class UserManagerHibernateRepo extends HibernateBaseRepo<User> implements UserManagerRepo{
	@Autowired
	public UserManagerHibernateRepo(SessionFactory sessionFactory) {
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
	@Deprecated
	@Override
	public List<Comment> getCommentsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}



}
