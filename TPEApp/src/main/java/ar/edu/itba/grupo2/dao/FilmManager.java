package ar.edu.itba.grupo2.dao;

import java.util.List;

import ar.edu.itba.grupo2.model.Film;

public interface FilmManager {
	
	public Film getFilmById(final int id);
	
	public List<Film> getAllFilms();
	
	public void saveFilm(Film film);
	
	public void deleteFilm(Film film);
	
	
}
