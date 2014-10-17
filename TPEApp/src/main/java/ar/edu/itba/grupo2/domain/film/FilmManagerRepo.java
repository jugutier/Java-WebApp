package ar.edu.itba.grupo2.domain.film;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.grupo2.domain.comment.Comment;

public interface FilmManagerRepo {
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
