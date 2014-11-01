package ar.edu.itba.grupo2.domain.comment;

import java.util.List;

public interface CommentRepo {
	
	public Comment get(final int id);

	public List<Comment> getAll();
	
	public List<Comment> getAllReported();

	public Comment save(Comment comment);

}
