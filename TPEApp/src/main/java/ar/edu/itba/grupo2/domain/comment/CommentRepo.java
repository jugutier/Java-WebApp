package ar.edu.itba.grupo2.domain.comment;

import java.util.List;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;

public interface CommentRepo {
	
	public Comment get(final int id);

	public List<Comment> getAll();
	
	public List<Comment> getAllReported();

	public Comment save(Comment comment);

}
