package ar.edu.itba.grupo2.domain.film;

import java.util.List;

import ar.edu.itba.grupo2.domain.genre.Genre;

public interface FilmRepo {

	public Film get(final int id);

	public List<Film> getAll();

	public Film save(Film film);
	
	public void delete(final Film film);

	public Genre getGenre(String genre);

	public List<Genre> getGenres();

	// Filter methods
	public List<Film> getTop(int amount);

	public List<Film> getLatest(int amount);

	public List<Film> getNewests(int dayTolerance);

	public List<Film> getFromGenre(Genre genre);

	public List<Film> getFromDirector(String director);

	public List<Film> getFiltered(Genre genre, String director);
	
	public List<Film> getByReleaseDate();
	
	public List<Film> getMostVisited(int amount);

}
