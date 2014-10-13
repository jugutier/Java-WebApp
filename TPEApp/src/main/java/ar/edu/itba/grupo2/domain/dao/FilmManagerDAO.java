package ar.edu.itba.grupo2.domain.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;

public interface FilmManagerDAO {
	@Transactional
	public Film get(final int id);
	@Transactional
	public List<Film> getAllFilms();
	@Transactional
	public Film save(Film film);
	@Transactional	
	public List<Comment> getCommentsForFilm(final Film film);
	@Transactional	
	public Comment saveComment(Comment c);
	
}
