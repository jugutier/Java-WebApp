package ar.edu.itba.grupo2.domain.film;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.baseRepo.HibernateBaseRepo;
import ar.edu.itba.grupo2.domain.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
@Repository
public class FilmManagerHibernateImpl extends HibernateBaseRepo<Film> implements FilmManagerDAO{
	
	@Autowired
	public FilmManagerHibernateImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		// TODO Auto-generated method stub
		return null;
	}

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
