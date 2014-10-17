package ar.edu.itba.grupo2.service;

import java.util.List;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmNotFoundException;
import ar.edu.itba.grupo2.domain.user.User;

public interface FilmService {
	@Deprecated
	public abstract List<Film> getAllFilms();
	@Deprecated
	public abstract Film getFilmById(int id) throws FilmNotFoundException;
	@Deprecated
	public abstract List<Comment> getCommentsForFilm(Film film)
			throws FilmNotFoundException;
	@Deprecated
	public abstract List<String> getGenres();
	//metodo de instancia en film
	public abstract boolean userHasCommentedFilm(Film film, User user)
			throws FilmNotFoundException;
	//al repo de peliculas
	public abstract List<Film> filterTopFilms(List<Film> filmList, int topAmount);

	public abstract List<Film> filterRecentlyAdded(List<Film> filmList,
			int amount);

	public abstract List<Film> filterNewReleases(List<Film> filmList,
			int dayTolerance);

	public abstract List<Film> filterByGenre(List<Film> filmList, String genre);

	public abstract List<Film> filterByDirector(List<Film> filmList,
			String director);

	public abstract List<Film> orderByReleaseDate(List<Film> filmList);

	public abstract boolean userCanComment(Film film, User user)
			throws FilmNotFoundException;
	@Deprecated
	public abstract Comment addComment(Comment comment);

}