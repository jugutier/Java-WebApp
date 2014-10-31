package ar.edu.itba.grupo2.domain.report;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.user.User;

@Entity
@Table(name="Report")
public class Report extends EntityBaseType {
	
	@ManyToOne private Comment comment;
	@OneToOne private User user;
	
	Report(){}
	
	public Report(Comment comment, User user) {
		setId(null);
		this.comment = comment;
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}
