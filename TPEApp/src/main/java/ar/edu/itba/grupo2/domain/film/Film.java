package ar.edu.itba.grupo2.domain.film;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.domain.user.User;

@Entity
public class Film extends EntityBaseType {

	@Column(length = 100, nullable = false)
	private String name;
	@Column(length = 40, nullable = false)
	private String director;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date creationDate;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date releaseDate;
	@ManyToMany
	private List<Genre> genres;
	@Column(length = 500, nullable = false)
	private String description;
	@Column(nullable = false)
	private int length;
	@Column(nullable = false)
	private int sumComments;
	@Column(nullable = false)
	private int totalComments;
	@OneToOne(mappedBy = "film", cascade = CascadeType.ALL,optional = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private MovieImage movieImage;

	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
	private List<Comment> comments;

	Film() {

	}

	private Film(final Builder builder) {
		setId(builder.id);
		this.name = builder.name;
		this.director = builder.director;
		this.creationDate = builder.creationDate;
		if(this.creationDate == null){
			this.creationDate = new Date();
		}
		this.releaseDate = builder.releaseDate;
		if(this.releaseDate == null){
			this.releaseDate = new Date();
		}
		this.genres = builder.genres;
		if(this.genres == null){
			this.genres =  new LinkedList<Genre>();
		}
		this.description = builder.description;
		this.length = builder.length;
		this.sumComments = builder.sumComments;
		this.totalComments = builder.totalComments;
		this.movieImage = builder.movieImage;
		this.comments = builder.comments;
		if(this.comments == null){
			this.comments = new LinkedList<Comment>();
		}
	}

	public String getName() {
		return name;
	}

	public String getDirector() {
		return director;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public String getDescription() {
		return description;
	}

	public int getLength() {
		return length;
	}

	public int getSumComments() {
		return sumComments;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public double getScore() {
		return totalComments == 0 ? 0 : (double) sumComments / totalComments;
	}

	public MovieImage getMovieImage() {
		return movieImage;
	}

	public List<Comment> getComments() {
		List<Comment> copy = new ArrayList<Comment>(comments.size());
		copy.addAll(comments);
		return copy;
	}
	
	public List<Comment> getCommentsForUser(User user) {
		List<Comment> copy = new ArrayList<Comment>(comments.size());
		if (user != null) {
			for (Comment c : comments) {
				c.belongsToUser = c.getUser().equals(user);
				c.reportable = !c.isReportedByUser(user);
				c.ratedByUser = c.isRatedBy(user);
			}
		}
		
		copy.addAll(comments);
		Collections.sort(copy, new Comparator<Comment>(){

			@Override
			public int compare(Comment c1, Comment c2) {
				if(c1.getRate() > c2.getRate())
					return -1;
				else if(c1.getRate() < c2.getRate())
					return 1;
				return 0;
			}
			
		});
		
		return copy;
	}

	public boolean userHasCommented(User user) {
		for (Comment c : comments) {
			if (c.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	public boolean userCanComment(User user) {
		if(userHasCommented(user)){
			return false;
		}		
		return (isReleased() || user.isVip() );
	}

	public void addComment(Comment c) throws UserCantCommentException {
		if (userCanComment(c.getUser())) {
			totalComments++;
			sumComments+=c.getFilmRate();
			comments.add(c);
		} else {
			throw new UserCantCommentException();
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setGenres(List<Genre> genres) {
		if (this.genres != null) {
			this.genres.clear();
			if (genres != null)
				this.genres.addAll(genres);
		}
		else {
			this.genres = genres;
		}
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setFilmImage(MovieImage image) {
		if (this.movieImage != null) {
			this.movieImage.setFilm(null);
		}
		this.movieImage = image;
	}

	public void removeComment(Comment c){
		comments.remove(c);
		sumComments-=c.getRate();
		totalComments--;

	}

	@Override
	public String toString() {
		return this.name + " - " + this.director;
	}

	public boolean isReleased() {
		Date today = new Date();
		if (today.after(this.releaseDate))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
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
		Film other = (Film) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}

	public static class Builder {
		private Integer id = -1;
		private String name;
		private String director;
		private Date releaseDate;
		private List<Genre> genres;
		private String description;
		private int length;
		private int totalComments;
		private int sumComments;
		private Date creationDate;
		private MovieImage movieImage = null;

		List<Comment> comments;

		public Builder id(final Integer id) {
			this.id = id;
			return this;
		}

		public Builder name(final String name) {
			this.name = name;
			return this;
		}

		public Builder director(final String director) {
			this.director = director;
			return this;
		}

		public Builder creationDate(final Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public Builder releaseDate(final Date releaseDate) {
			this.releaseDate = releaseDate;
			return this;
		}

		public Builder genres(final List<Genre> genres) {
			this.genres = genres;
			return this;
		}

		public Builder description(final String description) {
			this.description = description;
			return this;
		}

		public Builder length(final int length) {
			this.length = length;
			return this;
		}

		public Builder totalComments(final int totalComments) {
			this.totalComments = totalComments;
			return this;
		}

		public Builder sumComments(final int sumComments) {
			this.sumComments = sumComments;
			return this;
		}

		public Builder movieImage(final MovieImage movieImage) {
			this.movieImage = movieImage;
			return this;
		}

		public Builder comments(final List<Comment> comments) {
			this.comments = comments;
			return this;
		}

		public Film build() {
			return new Film(this);
		}
	}

}
