package ar.edu.itba.grupo2.domain.film;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;
@Repository
public class HibernateFilmRepo extends HibernateBaseRepo<Film> implements FilmRepo{
	
	@Autowired
	public HibernateFilmRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	@Override
	public List<Film> getTop(int amount) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Film> getLatest(int amount) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Film> getNewests(int dayTolerance) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Film> getFromGenre(Genre genre) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Film> getFromDirector(String director) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Film> getByReleaseDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Genre> getGenres() {
		Genre[] genres = Genre.values();
		List<Genre> ret = new LinkedList<Genre>();
		for (Genre genre : genres) {
			ret.add(genre);
		}
		return ret;
	}
	
}
