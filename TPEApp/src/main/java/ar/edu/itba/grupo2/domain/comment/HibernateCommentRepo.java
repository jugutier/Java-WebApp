package ar.edu.itba.grupo2.domain.comment;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;
import ar.edu.itba.grupo2.domain.film.Film;

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
	
	@Override
	public List<Comment> getReportedUnresolved() {
		return find("SELECT distinct c  FROM Comment as c join fetch c.reports as report WHERE size(c.reports) > 0 and report.reportResolution is null");
	}

}
