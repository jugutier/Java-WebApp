package ar.edu.itba.grupo2.web.command;

public class CommentForm {
	private int filmId;
	private int rating;
	private String comment;
	
	public CommentForm (){
	}
	
	public int getRating(){
		return this.rating;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	public int getFilmId(){
		return this.filmId;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setFilmId(int filmId){
		this.filmId = filmId;
	}
}
