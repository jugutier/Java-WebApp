package ar.edu.itba.grupo2.dao.PSQLImpl;

import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;

public class FilmManagerPSQLImpl implements FilmManagerDAO{

	@Override
	public Film getFilmById(int id) throws FilmNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Film> getAllFilms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveFilm(Film film) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFilm(Film film) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> getCommentsForFilm(Film film) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommentToFilm(Film film, Comment comment) {
		// TODO Auto-generated method stub
		
	}

}
