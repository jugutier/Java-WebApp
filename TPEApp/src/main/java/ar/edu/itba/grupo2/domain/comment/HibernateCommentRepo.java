package ar.edu.itba.grupo2.domain.comment;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;

@Repository
public class HibernateCommentRepo extends HibernateBaseRepo<Comment> implements CommentRepo {

	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Comment> getAllReported() {
		return find("FROM Comment c WHERE size(c.reports) > 0 ORDER BY size(c.reports) DESC");
	}

}
