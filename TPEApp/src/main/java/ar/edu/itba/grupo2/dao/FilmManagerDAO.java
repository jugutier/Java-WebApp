package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;

public interface FilmManagerDAO {
	
	public Film getFilmById(final int id) throws FilmNotFoundException;
	
	public List<Film> getAllFilms();
	
	public Film saveFilm(Film film);
	
	public void deleteFilm(final Film film);
	
	public List<Comment> getCommentsForFilm(final Film film);
	
	public void addCommentToFilm(final Film film, final Comment comment);
	
}
