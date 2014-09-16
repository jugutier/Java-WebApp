package ar.edu.itba.it.paw.model;

import java.util.Date;

import ar.edu.itba.grupo2.model.BaseType;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.Film.Builder;

public class User extends BaseType {
	
	private String name;
	private String lastname;
	private String password;
	private String email;
	private Date birthdate;
	private boolean vip;
	private String secretQuestion;
	private String secretAnswer;
	
	private User(final Builder builder){
		setId(builder.id);
		this.name = builder.name;
		this.lastname = builder.lastname;
		this.email = builder.email;
		this.password = builder.password;
		this.birthdate = builder.birthdate;
		this.vip = builder.vip;
		this.secretQuestion = builder.secretQuestion;
		this.secretAnswer = builder.secretAnswer;
	}
	
	public static class Builder {
		private String name;
		private String lastname;
		private Date birthdate;
		private String email;
		private String password;
		private Integer id = -1;
		private boolean vip = false;
		private String secretQuestion;
		private String secretAnswer;
		


		public Builder name(final String name){
			this.name=name;
			return this;
		}
		
		public Builder lastname(final String lastname){
			this.lastname=lastname;
			return this;
		}
		
		public Builder vip(final boolean vip){
			this.vip=vip;
			return this;
		}
		
		public Builder email(final String email){
			this.email=email;
			return this;
		}
		
		public Builder password(final String password){
			this.password=password;
			return this;
		}
		
		public Builder birthdate(final Date birthdate){
			this.birthdate=birthdate;
			return this;
		}
		
		public Builder id(final Integer id) {
			this.id = id;
			return this;
		}
		
		public Builder secretQuestion(final String secretQuestion){
			this.secretQuestion = secretQuestion;
			return this;
		}
		
		public Builder secretAnswer(final String secretAnswer){
			this.secretAnswer = secretAnswer;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
}
	
	public String getName(){
		return name;
	}
	
	public boolean getVip(){
		return vip;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}
	
	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}
	
}
