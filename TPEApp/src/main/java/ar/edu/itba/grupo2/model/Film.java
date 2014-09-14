package ar.edu.itba.grupo2.model;

import java.util.Date;

public class Film extends BaseType {

	private String name;
	private String director;
	private Date creationDate;
	private Date releaseDate;
	private String genre;
	private String description;
	private int length;
	private int sumComments;
	private int totalComments;

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

	public String getGenre() {
		return genre;
	}

	public String getDescription() {
		return description;
	}

	public int getLength() {
		return length;
	}

	public double getScore() {
		return sumComments / totalComments;
	}

	public static class Builder {
		private String name;
		private String director;
		private Date releaseDate;
		private String genre;
		private String description;
		private int length;
		private int totalComments;
		private int sumComments;
		private Integer id = -1;
		private Date creationDate;

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
			this.genre = genre;
			return this;
		}

		public Builder description(final String description) {
			this.description = description;
			return this;
		}

		public Builder id(final Integer id) {
			this.id = id;
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

		public Film build() {
			return new Film(this);
		}
	}

	@Override
	public String toString() {
		return this.name + " - " + this.director;
	}
}
