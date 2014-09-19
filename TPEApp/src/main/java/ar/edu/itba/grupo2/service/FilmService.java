package ar.edu.itba.grupo2.service;

import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.User;

public interface FilmService {

	public abstract List<Film> getAllFilms();

	public abstract Film getFilmById(int id) throws FilmNotFoundException;

	public abstract List<Comment> getCommentsForFilm(Film film)
			throws FilmNotFoundException;

	public abstract List<String> getGenres();

	public abstract boolean userHasCommentedFilm(Film film, User user)
			throws FilmNotFoundException;

	public abstract List<Film> filterTopFilms(List<Film> filmList, int topAmount);

	public abstract List<Film> filterRecentlyAdded(List<Film> filmList,
			int amount);

	public abstract List<Film> filterNewReleases(List<Film> filmList,
			int dayTolerance);

	public abstract List<Film> filterByGenre(List<Film> filmList, String genre);

	public abstract List<Film> filterByDirector(List<Film> filmList,
			String director);

	public abstract List<Film> orderByReleaseDate(List<Film> filmList);

	public abstract Comment saveComment(Comment comment);

	public abstract boolean userCanComment(Film film, User user)
			throws FilmNotFoundException;

	public abstract Comment addComment(Comment comment);

}