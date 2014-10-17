package ar.edu.itba.grupo2.domain.film;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;
@Repository
public class FilmManagerHibernateRepo extends HibernateBaseRepo<Film> implements FilmManagerRepo{
	
	@Autowired
	public FilmManagerHibernateRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	@Deprecated
	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		// TODO Auto-generated method stub
		return null;
	}
	@Deprecated
	@Override
	public Comment saveComment(Comment c) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getAllFilms() {
		return (List<Film>) super.getAll();
	}

	
}
