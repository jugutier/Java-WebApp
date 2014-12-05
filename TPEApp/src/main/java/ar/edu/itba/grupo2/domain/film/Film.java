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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

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
	/*@OneToOne(mappedBy = "film", cascade = CascadeType.ALL,optional = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private MovieImage movieImage;*/
	@OneToOne(cascade = CascadeType.ALL,optional = true)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@JoinColumn(name = "image_fk")
	private MovieImage movieImage;
	@Column(nullable = false)
	private int visits;
	
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
		this.visits = builder.visits;
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
	
	public int getVisits() {
		return visits;
	}
	
	public int getStock() {
		// TODO Get stock from external API
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		query.whereEqualTo("name", "Volver al futuro 3");
		/*query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> list, ParseException e) {
		        if (e == null) {
		        	System.out.println(list.get(0).getInt("stock"));
		        } else {
		        	
		        }
		    }
		});
		return 3;*/
		try {
			return query.find().get(0).getInt("stock");
		} catch (ParseException e) {
			return 0;
		}
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
				c.setBelongsToUser(c.getUser().equals(user));
				c.setReportable(!c.isBelongsToUser() && !c.isReportedByUser(user));
				c.setRatedByUser(c.isRatedBy(user));
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

	private boolean userHasCommented(User user) {
		for (Comment c : comments) {
			if (c.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	public boolean userCanComment(User user) {
		if(user == null)
			return false;
		if(userHasCommented(user)){
			return false;
		}		
		return (isReleased() || user.isVip() );
	}

	public void addComment(Comment c) throws UserCantCommentException {
		if (userCanComment(c.getUser())) {
			totalComments++;
			sumComments+=c.getFilmRate();
			if(!comments.contains(c)){
				comments.add(c);
				c.getUser().addComment(c);
			}
		} else {
			throw new UserCantCommentException();
		}
	}
	
	public void removeComment(Comment c){
		if(comments.contains(c)){
			comments.remove(c);
			c.getUser().removeComment(c);
			sumComments -= c.getFilmRate();
			totalComments--;
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
		this.genres = genres;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setFilmImage(MovieImage image) {
		/*if (this.movieImage != null) {
			this.movieImage.setFilm(null);
		}*/
		this.movieImage = image;
	}

	public boolean isReleased() {
		Date today = new Date();
		if (today.after(this.releaseDate))
			return true;
		return false;
	}
	
	public void visitFilm() {
		visits++;
		return;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.director;
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
		private int length = 0;
		private int totalComments = 0;
		private int sumComments = 0;
		private Date creationDate = null;
		private MovieImage movieImage = null;
		private int visits = 0;

		private List<Comment> comments;

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

		public Builder visits(final int visits) {
			this.visits = visits;
			return this;
		}
		
		public Film build() {
			return new Film(this);
		}
	}

}
