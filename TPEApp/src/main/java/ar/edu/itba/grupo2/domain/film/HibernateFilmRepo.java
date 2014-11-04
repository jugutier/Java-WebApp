package ar.edu.itba.grupo2.domain.film;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.grupo2.domain.common.HibernateBaseRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;

@Repository
public class HibernateFilmRepo extends HibernateBaseRepo<Film> implements
		FilmRepo {

	@Autowired
	public HibernateFilmRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Film> getTop(int amount) {
		return limitedFind(
				"FROM Film where totalComments > 0 ORDER BY (sumComments / totalComments) ASC",
				amount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getLatest(int amount) {
		Criteria c = createCriteria().add(
				Restrictions.lt("creationDate", new Date()));
		c.setMaxResults(amount);
		return (List<Film>) c.list();
	}

	// newest: Between today and +dayTolerance from now
	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getNewests(int dayTolerance) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dayTolerance);
		return createCriteria().add(
				Restrictions.between("releaseDate", new Date(), cal.getTime()))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getFromGenre(Genre genre) {
		return find("FROM Film film WHERE ? IN (FROM film.genres) ORDER BY releaseDate ASC", genre);
		//return createCriteria()
		//		.add(Restrictions.in("genres", genre)).list();
		//return find("FROM Film f WHERE ? in ?");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getFromDirector(String director) {
		///return createCriteria().add(Restrictions.ilike("director", director))
		//		.list();
		return find("FROM Film f WHERE upper(f.director) LIKE ? ORDER BY releaseDate ASC", "%" + director.toUpperCase() + "%");
	}
	
	@Override
	public List<Film> getFiltered(Genre genre, String director) {
		if (director == null) {
			director = "";
		}
		
		if (genre == null) {
			return getFromDirector(director);
		}
		
		return find("FROM Film f WHERE upper(f.director) LIKE ? AND ? IN (FROM f.genres) ORDER BY releaseDate ASC", "%" + director.toUpperCase() + "%", genre);
	}

	@Override
	public List<Film> getByReleaseDate() {
		return find("FROM Film ORDER BY releaseDate ASC");
	}

	@Override
	public Genre getGenre(String genre) {
		List<Genre> list = find("from Genre where genre = ?", genre);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Genre> getGenres() {
		List<Genre> list = find("from Genre");
		return list;
	}

}
