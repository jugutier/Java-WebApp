package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;

public interface FilmManagerDAO {
	
	public Film getFilmById(final int id) throws FilmNotFoundException;
	
	public List<Film> getAllFilms();
	
	public Film saveFilm(Film film);
		
	public List<Comment> getCommentsForFilm(final Film film) throws FilmNotFoundException;
	/**
	 * 
	 * @param film
	 * @param comment
	 * @return same comment with the generated id
	 */
	public Comment addCommentToFilm(final Film film, final Comment comment);
	
}
