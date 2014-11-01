package ar.edu.itba.grupo2.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.comment.CommentRepo;

@Component
public class CommentConverter implements Converter<String, Comment> {

	private final CommentRepo commentRepo;
	
	@Autowired
	public CommentConverter(CommentRepo commentRepo) {
		this.commentRepo = commentRepo;
	}

	@Override
	public Comment convert(String id) {
		return commentRepo.get(Integer.valueOf(id));
	}
}
