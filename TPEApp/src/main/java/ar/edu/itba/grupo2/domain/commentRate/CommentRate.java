package ar.edu.itba.grupo2.domain.commentRate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.user.User;

@Entity
@Table(name="CommentRate")
public class CommentRate extends EntityBaseType {
	
	@ManyToOne private User user;
	@ManyToOne private Comment comment;
	@Column(nullable=false)private int rating;
	
	CommentRate(){}
	
	public CommentRate(Comment comment, User user, int rating) {
		this.user = user;
		this.rating = rating;
		this.comment = comment;
	}
	
	public int getRating(){
		return this.rating;
	}
	
	public User getUser(){
		return this.user;
	}
}
