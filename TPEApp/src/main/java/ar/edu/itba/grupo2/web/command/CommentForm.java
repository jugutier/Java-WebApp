package ar.edu.itba.grupo2.web.command;

import ar.edu.itba.grupo2.domain.comment.Comment;

public class CommentForm {
	private int filmId;
	private int rating;
	private String text;
	//private Comment comment;
	
	public CommentForm (){
	}
	
	public int getRating(){
		return this.rating;
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
	
	//public void setComment(Comment comment){
	//	this.comment = comment;
	//}
	
	public void setFilmId(int filmId){
		this.filmId = filmId;
	}
	
	public void setText(String text){
		this.text = text;
	}
}
