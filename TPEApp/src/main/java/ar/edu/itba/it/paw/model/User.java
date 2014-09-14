package ar.edu.itba.it.paw.model;

import java.util.Date;

import ar.edu.itba.grupo2.model.BaseType;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.Film.Builder;

public class User extends BaseType {
	
	private String name;
	private String surname;
	private String password;
	private String email;
	private Date birthdate;
	
	private User(final Builder builder){
		setId(builder.id);
		this.name = builder.name;
		this.surname = builder.surname;
		this.email = builder.email;
		this.password = builder.password;
		this.birthdate = builder.birthdate;
	}
	
	public static class Builder {
		private String name;
		private String surname;
		private Date birthdate;
		private String email;
		private String password;
		private Integer id = -1;
		
		public Builder name(final String name){
			this.name=name;
			return this;
		}
		
		public Builder surname(final String surname){
			this.surname=surname;
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
		
		public User build() {
			return new User(this);
		}
}
	
	public String getName(){
		return name;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}

	public String getSurname() {
		return surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}
}
