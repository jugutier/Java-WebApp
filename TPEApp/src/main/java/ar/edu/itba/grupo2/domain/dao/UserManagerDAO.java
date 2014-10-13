package ar.edu.itba.grupo2.domain.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;

public interface UserManagerDAO {
	@Transactional
	public User get(final int id);
	@Transactional
	public User getUserByEmail(final String email);
	@Transactional
	public User saveUser(final User user);
	@Transactional
	public List<Comment> getCommentsByUser(final User user);

}
