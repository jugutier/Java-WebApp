package ar.edu.itba.grupo2.domain.comment;

import java.util.List;

import ar.edu.itba.grupo2.domain.film.Film;

public interface CommentRepo {
	
	public Comment get(final int id);

	public List<Comment> getAll();
	
	public List<Comment> getAllReported();
	
	public List<Comment> getReportedUnresolved();

	public Comment save(Comment comment);

}
