package ar.edu.itba.grupo2.domain.report;

import javax.persistence.Column;
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
	@Column(length=140,nullable=true)private String reason;
	@ManyToOne private ReportResolution reportResolution;
	
	Report(){}
	
	public Report(Comment comment, User user, String reason) {
		setId(null);
		this.comment = comment;
		this.user = user;
		this.reason = reason;
		this.reportResolution = null;
	}
	
	public User getUser() {
		return user;
	}
	
	public void resolve(ReportResolution reportResolution){
		this.reportResolution = reportResolution;
	}
	
	public boolean resolved(){
		return reportResolution != null;
	}

}
