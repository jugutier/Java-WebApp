package ar.edu.itba.grupo2.model;

import java.util.Date;

public class Comment extends BaseType {

	private Film film;
	private User user;
	private Date creationDate;
	private String text;
	private int rate;

	private Comment(final Builder builder) {
		setId(builder.id);
		this.film = builder.film;
		this.user = builder.user;
		this.creationDate = builder.creationDate;
		this.text = builder.text;
		this.rate = builder.rate;
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

	public int getRate() {
		return rate;
	}

	public static class Builder {
		private int id;
		private Film film;
		private User user;
		private Date creationDate;
		private String text;
		private int rate;

		public Builder id(final int id) {
			this.id = id;
			return this;
		}

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
}
