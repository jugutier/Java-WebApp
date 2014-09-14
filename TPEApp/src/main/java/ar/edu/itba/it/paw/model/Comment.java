package ar.edu.itba.it.paw.model;

public class Comment {
	
	public User user;
	public String message;
	
	public Comment(User user, String message){
		this.user = user;
		this.message = message;
	}
	
//	public String getUsername(){
//		return user.getUsername();
//	}
	
	public String getEmail(){
		return user.getEmail();
	}
	
	public String getMessage(){
		return message;
	}
}
