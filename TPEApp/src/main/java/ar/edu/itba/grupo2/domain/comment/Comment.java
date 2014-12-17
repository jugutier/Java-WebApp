package ar.edu.itba.grupo2.domain.comment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import ar.edu.itba.grupo2.domain.commentRate.CommentRate;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.report.Report;
import ar.edu.itba.grupo2.domain.report.ReportResolution;
import ar.edu.itba.grupo2.domain.user.User;
@Entity
@Table(name="Comment")
public class Comment extends EntityBaseType {

	@ManyToOne private Film film;
	@ManyToOne private User user;
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy="comment", cascade = CascadeType.ALL)
	private List<Report> reports;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false)private Date creationDate;
	@Column(length=140,nullable=false)private String text;
	@Column(nullable=false)private int rate;
	@OneToMany(mappedBy="comment", cascade = CascadeType.ALL)
	private List<CommentRate> ratings;
	
	Comment(){}
	
	private Comment(final Builder builder) {
		setId(builder.id);
		this.film = builder.film;
		this.user = builder.user;
		this.reports = new LinkedList<Report>();
		this.creationDate = builder.creationDate;
		if(this.creationDate == null){
			this.creationDate = new Date();
		}
		setText(builder.text);
		this.rate = builder.rate;
		this.ratings = new LinkedList<CommentRate>();
	}

	public Film getFilm() {
		return film;
	}

	public User getUser() {
		return user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getText() {
		return text;
	}

	public float getRate() {
		int count = 0, sum = 0;
		for(CommentRate cr : ratings){
			count++;
			sum += cr.getRating();
		}
		return count == 0 ? 0 : ((float)sum)/count;
	}
	
	public boolean isRated(){
		return !ratings.isEmpty();
	}
	
	public int getFilmRate() {
		return this.rate;
	}
	
	public void rate(User user, int rating){
		if (user != null && !this.user.equals(user) && !isRatedBy(user)){
			ratings.add(new CommentRate(this, user, rating));
		}
	}
	
	public boolean isRatedBy(User user) {
		if (user != null) {
			for (CommentRate cr : ratings) {
				if(cr.getUser().equals(user)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isReportedByUser(User user) {
		boolean found = false;
		if (user != null) {
			for (Report r : reports) {
				found = found || r.getUser().equals(user);
			}
		}
		
		return found;
	}
	
	public void remove() {
		film.removeComment(this);
	}
	
	public void report(User user, String reason) {
		if (user != null && reason != null && !this.user.equals(user) && !isReportedByUser(user) )
			reports.add(new Report(this, user, reason));
	}
	
	public int getReportCount() {
		if (reports == null) {
			return 0;
		}
		
		//return reports.size();
		return getUnresolvedReports().size();
	}
	
	public boolean isReported() {
		//return reports != null && !reports.isEmpty();
		for(Report r: reports){
			if(!r.resolved()){
				return true;
			}
		}
		return false;
	}
	
	public void discardReports() {
		if (reports != null)
			reports.clear();
	}
	
	public void resolve(ReportResolution rr/*Resolution resolution, String reason*/){
		if (rr == null) 
			return;
		
		markReports(rr);
		
	}
	
	private void markReports(ReportResolution resolution){
		List<Report> unresolved = getUnresolvedReports();
		
		for(Report r: unresolved){
			r.resolve(resolution);
		}
		return ;
	}
	
	public List<Report> getUnresolvedReports(){
		List<Report> unresolved = new LinkedList<Report>(); 
		for(Report r: reports){
			if(!r.resolved()){
				unresolved.add(r);
			}
		}
		return unresolved;
	}
	
	private void setText(String text) {
		if(text == null || text.equals("") || text.length()>140){
			throw new IllegalArgumentException();
		}
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.user + " > " + this.text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (film == null) {
			if (other.film != null)
				return false;
		} else if (!film.equals(other.film))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	public static class Builder {
		private Integer id = null;
		private Film film;
		private User user;
		private Date creationDate;
		private String text;
		private int rate;
	
		public Builder film(final Film film) {
			this.film = film;
			return this;
		}
	
		public Builder user(final User user) {
			this.user = user;
			return this;
		}
	
		public Builder creationDate(final Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}
	
		public Builder text(String text) {
			this.text = text;
			return this;
		}
	
		public Builder rate(final int score) {
			this.rate = score;
			return this;
		}
	
		public Comment build() {
			return new Comment(this);
		}
	}
}
