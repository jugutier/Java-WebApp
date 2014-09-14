package ar.edu.itba.grupo2.model;

import java.util.Date;

public class Film extends BaseType {
	
	private String name;
	private String director;
	private Date releaseDate;
	private String genre;
	private String description;
	private int length;
	public int score;
	
	private Film(final Builder builder) {
		setId(builder.id);
		this.name = builder.name;
		this.director = builder.director;
		this.releaseDate = builder.releaseDate;
		this.genre = builder.genre;
		this.description = builder.description;
		this.length = builder.length;
		this.score = builder.score;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDirector() {
		return director;
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
	
	public int getScore() {
		return score;
	}

	public static class Builder {
		private String name;
		private String director;
		private Date releaseDate;
		private String genre;
		private String description;
		private int length;
		private int score;
		private Integer id = -1;
		
		public Builder name(final String name) {
			this.name = name;
			return this;
		}
		
		public Builder director(final String director) {
			this.director = director;
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
		
		public Builder score(final int score) {
			this.score = score;
			return this;
		}
		
		public Film build() {
			return new Film(this);
		}
	}
}
