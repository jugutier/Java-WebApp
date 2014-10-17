package ar.edu.itba.grupo2.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;


@Entity
@Table(name="GAJAmdbUser")

public class User extends EntityBaseType {
	
	@Column(length=15,nullable=false)private String name;
	@Column(length=15,nullable=false)private String lastname;
	@Column(length=10,nullable=false)private String password;
	@Column(length=100,nullable=false,unique=true)private String email;
	@Temporal(TemporalType.DATE)@Column(name="birthdate", nullable=false)private Date birthdate;
	@Column(nullable=false)private boolean vip;
	@Column(length=140,nullable=false)private String secretQuestion;
	@Column(length=140,nullable=false)private String secretAnswer;
	
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
	
	public boolean isVip(){
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
	
	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
