package ar.edu.itba.grupo2.domain.film;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
				"FROM Film where totalComments > 0 ORDER BY (sumComments / totalComments) DESC",
				amount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> getLatest(int amount) {
		Criteria c = createCriteria().add(
				Restrictions.le("creationDate", new Date()));
		c.setMaxResults(amount);
		List<Film> out = new ArrayList<Film>(c.list().size());
		out.addAll(c.list());
		Collections.sort(out, new Comparator<Film>(){

			@Override
			public int compare(Film o1, Film o2) {
				return -o1.getCreationDate().compareTo(o2.getCreationDate());
			}
			
		});
		return out;
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

	@Override
	public List<Film> getMostVisited(int amount) {
		return limitedFind(
				"FROM Film where visits > 0 ORDER BY visits DESC",
				amount);
	}

	@Override
	public List<Film> getFromGenre(Genre genre) {
		return find("FROM Film film WHERE ? IN (FROM film.genres) ORDER BY releaseDate ASC", genre);
	}

	@Override
	public List<Film> getFromDirector(String director) {
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
	/**
	 * Validation using natural key is useful when importing films from a CSV. 
	 * Otherwise the model will validate itself.
	 */
	@Override
	public Film save(Film entity) {
		List<Film> duplicateFilm = find("from Film where name = ? AND creationDate = ? ",entity.getName(),entity.getCreationDate());
		if(duplicateFilm == null || duplicateFilm.size() !=0){
			throw new DuplicateFilmException();
		}
		return super.save(entity);
	}

}
