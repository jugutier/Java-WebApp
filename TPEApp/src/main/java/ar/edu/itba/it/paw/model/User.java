package ar.edu.itba.it.paw.model;

public class User {
	
	private String username;
	private String password;
	private String email;
	
	public User(String username, String password, String email){
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}
}
