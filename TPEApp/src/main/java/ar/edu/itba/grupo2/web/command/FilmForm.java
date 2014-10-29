package ar.edu.itba.grupo2.web.command;

import ar.edu.itba.grupo2.domain.comment.Comment;

public class FilmForm {
	private int filmId;
	private int rating;
	private String text;
	private Comment comment;
	
	public FilmForm (){
	}
	
	public int getRating(){
		return this.rating;
	}
	public Comment getComment(){
		return this.comment;
	}
	public String getText(){
		return this.text;
	}
	
	public int getFilmId(){
		return this.filmId;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public void setComment(String comment){
		this.text = comment;
	}
	
	public void setFilmId(int filmId){
		this.filmId = filmId;
	}
}
