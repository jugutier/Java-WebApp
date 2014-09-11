package ar.edu.itba.grupo2.model;

public class Comment extends BaseType {
	
	private String message;
	private String username;
	private int score;
	
	private Comment(final Builder builder) {
		this.message = builder.message;
		this.username = builder.username;
		this.score = builder.score;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return score;
	}
	
	public static class Builder {
		
		private String message;
		private String username;
		private int score;
		
		public Builder message(final String message) {
			this.message = message;
			return this;
		}
		
		public Builder username(final String username) {
			this.username = username;
			return this;
		}
		
		public Builder score(final int score) {
			this.score = score;
			return this;
		}
		
		public Comment build() {
			return new Comment(this);
		}
	}
}
