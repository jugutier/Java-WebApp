package ar.edu.itba.grupo2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.FilmManagerPSQLImpl;
import ar.edu.itba.grupo2.model.Film;

public class FilmService {
	
	private static FilmService instance = null;
	
	private FilmService(){
		
	}
	
	public static FilmService getInstance() {
		if (instance == null) {
			instance = new FilmService();
		}
		
		return instance;
	}
	
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
	
	public List<Film> filterTopFilms(final List<Film> filmList, final int topAmount) {
		final List<Film> result = new ArrayList<Film>();
		int originalSize = filmList.size();
		int top = (topAmount > originalSize)? originalSize : topAmount;
		
		for (Film f : filmList) {
			result.add(f);
		}
		
		Collections.sort(result, new Comparator<Film>(){

			@Override
			public int compare(Film arg0, Film arg1) {
				return Double.compare(arg0.getScore(), arg1.getScore());
			}
			
		});
		
		return result.subList(0, top);
	}
	
	public List<Film> filterRecentlyAdded(final List<Film> filmList, final int amount) {
		final List<Film> result = new ArrayList<Film>();
		int originalSize = filmList.size();
		int top = (amount > originalSize)? originalSize : amount;
		
		for (Film f : filmList) {
			result.add(f);
		}
		
		Collections.sort(filmList, new Comparator<Film>(){

			@Override
			public int compare(Film arg0, Film arg1) {
				return arg0.getCreationDate().compareTo(arg1.getCreationDate());
			}
			
		});
		
		return result.subList(0, top);
	}
	
	public List<Film> filterNewReleases(final List<Film> filmList, final int dayTolerance) {
		final List<Film> result = new ArrayList<Film>();
		final Date today = new Date();
		long startTime = 0;
		long endTime = 0;
		long diffTime = 0;
		long diffDays = 0;
		
		for (Film f : filmList) {
			startTime = f.getReleaseDate().getTime();
			endTime = today.getTime();
			diffTime = endTime - startTime;
			diffDays = diffTime / (1000 * 60 * 60 * 24);
			
			if (diffDays < dayTolerance && diffDays >= 0) {
				result.add(f);
			}
		}
		
		return result;
	}
}
