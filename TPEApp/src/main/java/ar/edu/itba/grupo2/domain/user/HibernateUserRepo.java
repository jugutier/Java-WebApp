package ar.edu.itba.grupo2.domain.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;

@Repository
public class HibernateUserRepo extends HibernateBaseRepo<User> implements
		UserRepo {
	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByEmail(String email) {
		List<User> list = createCriteria().add(Restrictions.eq("email", email))
				.list();
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User logIn(String email, String password) {
		Criteria c = createCriteria().add(Restrictions.eq("email", email)).add(
				Restrictions.eq("password", password));
		List<User> list = c.list();
		return list.size() == 0 ? null : list.get(0);
	}
	
	public List<Comment> getLatestComments(User u){
		List<User> followers = u.getFollows();
		List<Comment> comments = new ArrayList<Comment>();
		for (User user : followers) {
			Comment last = null;
			for (Comment comment : user.getComments()) {
				if(last == null){
					last = comment;
				}
				if(last.getCreationDate().before(comment.getCreationDate())){
					last = comment;
				}
			}
			
			if (last != null) {
				comments.add(last);
			}
		}
		
		return comments;
	}

	@Override
	public User registerUser(String email, String password,
			String passwordConfirm, String name, String lastname,
			Date birthdate, String secretQuestion, String secretAnswer) {
		if(!password.equals(passwordConfirm)){
			throw new PasswordDontMatchException();
		}
		User user = new User.Builder()
				.email(email)
				.password(password)
				.name(name)
				.lastname(lastname)
				.birthdate(birthdate)
				.secretQuestion(secretQuestion)
				.secretAnswer(secretAnswer)
				.build();
		return save(user);
	}

}
