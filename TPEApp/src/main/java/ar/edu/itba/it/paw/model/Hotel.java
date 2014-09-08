package ar.edu.itba.it.paw.model;

import java.util.ArrayList;

public class Hotel {
	
	private int code;
	private String name;
	private String description;
	private ArrayList<Comment> comments;
	
	public Hotel(int code, String name, String description){
		this.code = code;
		this.name = name;
		this.description = description;
		
		comments = new ArrayList<Comment>();
	}
	
	public void addComment(User user, String message){
		comments.add(new Comment(user, message));
	}
	
	public int getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public ArrayList<Comment> getComments(){
		return comments;
	}
}
