package ar.edu.itba.grupo2.dao.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManager;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;

public final class MemoryFilmManager implements FilmManager{
	
	private final HashMap<Integer, Film> film_list = new HashMap<Integer, Film>();
	private final List<CommentTuple> comment_list = new ArrayList<CommentTuple>();
	private int next_id = 0;
	
	private static FilmManager film_manager = null;
	
	private MemoryFilmManager(){
		
	}
	
	private static class CommentTuple {
		public Comment comment;
		public Film film;
		
		public CommentTuple(Comment comment, Film film){
			this.comment = comment;
			this.film = film;
		}
	};
	
	public static FilmManager getInstance(){
		if (film_manager == null){
			film_manager = new MemoryFilmManager();
		}
		
		return film_manager;
	}

	@Override
	public Film getFilmById(final int id) throws FilmNotFoundException{
		Film film = film_list.get(id);
		
		if (film == null) {
			throw new FilmNotFoundException();
		}
		
		return film;
	}

	@Override
	public List<Film> getAllFilms() {
		List<Film> list = new ArrayList<Film>(film_list.values());
		
		return list;
	}

	@Override
	public void saveFilm(final Film film) {
		if (film != null){
			if (film.isNew()) {
				film.setId(next_id);
			}
			
			film_list.put(next_id, film);
			
			next_id++;
		}
	}

	@Override
	public void deleteFilm(final Film film) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Comment> getCommentsForFilm(final Film film) {
		List<Comment> tmpList = new ArrayList<Comment>();
		
		for (CommentTuple c : comment_list) {
			if (film.getId() == c.film.getId()) {
				tmpList.add(c.comment);
			}
		}
		
		return tmpList;
	}
	
	@Override
	public void addCommentToFilm(final Film film, final Comment comment) {
		comment_list.add(new CommentTuple(comment, film));
	}

}
