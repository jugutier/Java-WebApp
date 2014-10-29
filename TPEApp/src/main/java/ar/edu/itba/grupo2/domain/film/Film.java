package ar.edu.itba.grupo2.domain.film;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.genre.Genre;
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
	@ManyToOne
	private Genre genre;
	@Column(length = 500, nullable = false)
	private String description;
	@Column(nullable = false)
	private int length;
	@Column(nullable = false)
	private int sumComments;
	@Column(nullable = false)
	private int totalComments;

	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
	private List<Comment> comments;

	Film() {

	}

	private Film(final Builder builder) {
		setId(builder.id);
		this.name = builder.name;
		this.director = builder.director;
		this.creationDate = builder.creationDate;
		this.releaseDate = builder.releaseDate;
		this.genre = builder.genre;
		this.description = builder.description;
		this.length = builder.length;
		this.sumComments = builder.sumComments;
		this.totalComments = builder.totalComments;
		this.comments = builder.comments;
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

	public Genre getGenre() {
		return genre;
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

	public List<Comment> getComments() {
		return comments;// TODO Return a copy?
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
		if (this.isReleased()) {
			if (this.userHasCommented(user)) {
				return false;
			} else {
				return true;
			}
		} else {
			if (user.isVip()) {
				if (this.userHasCommented(user)) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public void addComment(Comment c) throws UserCantCommentException {
		if (userCanComment(c.getUser())) {
			comments.add(c);
			c.getUser().addComment(c);
		} else {
			throw new UserCantCommentException();
		}
	}
	
	public void removeComment(Comment c) throws UserIsntAdminException {
		User user = c.getUser();
		if (!user.isAdmin()) {
			throw new UserIsntAdminException();
		}	
		comments.remove(c);
		user.removeComment(c);
	
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
		private Genre genre;
		private String description;
		private int length;
		private int totalComments;
		private int sumComments;
		private Date creationDate;
	
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
	
		public Builder genre(final String genre) {
			this.genre = new Genre(genre);
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
	
		public Builder comments(final List<Comment> comments) {
			this.comments = comments;
			return this;
		}
	
		public Film build() {
			return new Film(this);
		}
	}

}
