package ar.edu.itba.grupo2.dao.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManager;
import ar.edu.itba.grupo2.model.Film;

public final class MemoryFilmManager implements FilmManager{
	
	private final HashMap<Integer, Film> film_list = new HashMap<Integer, Film>();
	private int next_id = 0;
	
	private static FilmManager film_manager = null;
	
	private MemoryFilmManager(){
	}
	
	public static FilmManager getInstance(){
		if (film_manager == null){
			film_manager = new MemoryFilmManager();
		}
		
		return film_manager;
	}

	@Override
	public Film getFilmById(int id) {
		return film_list.get(id);
	}

	@Override
	public List<Film> getAllFilms() {
		List<Film> list = new ArrayList<Film>(film_list.values());
		
		return list;
	}

	@Override
	public void saveFilm(Film film) {
		if (film != null){
			if (film.isNew()) {
				film.setId(next_id);
			}
			
			film_list.put(next_id, film);
			
			next_id++;
		}
	}

	@Override
	public void deleteFilm(Film film) {
		// TODO Auto-generated method stub
		
	}

}
