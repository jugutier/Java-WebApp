package ar.edu.itba.grupo2.service;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.FilmManagerPSQLImpl;
import ar.edu.itba.grupo2.model.Film;

public class FilmService {
	public List<String> getGenres() {
		FilmManagerDAO fmDAO = new FilmManagerPSQLImpl();
		List<Film> films = fmDAO.getAllFilms();
		SortedSet<String> genres = new TreeSet<String>();
		for (Film f : films) {
			genres.add(f.getGenre());
		}
		List<String> ret = new LinkedList<String>();
		ret.addAll(genres);

		return ret;

	}
}
