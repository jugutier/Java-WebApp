package ar.edu.itba.grupo2.domain.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;

@Entity
@Table(name = "GAJAmdbUser")
public class User extends EntityBaseType {

	@Column(length = 15, nullable = false)
	private String name;
	@Column(length = 15, nullable = false)
	private String lastname;
	@Column(length = 10, nullable = false)
	private String password;
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	@Temporal(TemporalType.DATE)
	@Column(name = "birthdate", nullable = false)
	private Date birthdate;
	@Column(nullable = false)
	private boolean admin;
	@Column(nullable = false)
	private boolean vip;
	@Column(length = 140, nullable = false)
	private String secretQuestion;
	@Column(length = 140, nullable = false)
	private String secretAnswer;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	// TODO Uncomment to get free followers list!
	/*@ManyToMany(mappedBy="followers", cascade=CascadeType.ALL)
	@JoinTable(name="followers",
	 joinColumns=@JoinColumn(name="followedUserId"),
	 inverseJoinColumns=@JoinColumn(name="followerId")
	)*/
	@ManyToMany
	private List<User> follows;
	
	/*@ManyToMany
	@JoinTable(name="followers",
	 joinColumns=@JoinColumn(name="followerId"),
	 inverseJoinColumns=@JoinColumn(name="followedUserId")
	)
	private List<User> followers;*/

	private transient boolean isFollowable;

	User() {

	}

	private User(final Builder builder) {
		setId(builder.id);
		this.name = builder.name;
		this.lastname = builder.lastname;
		this.email = builder.email;
		if(this.email==null){
			throw new CantCreateWithoutEmailException();
		}
		this.password = builder.password;
		this.birthdate = builder.birthdate;
		this.vip = builder.vip;
		this.secretQuestion = builder.secretQuestion;
		this.secretAnswer = builder.secretAnswer;
		this.comments = builder.comments;
		if(this.comments == null){
			this.comments = new LinkedList<Comment>();
		}
		this.admin = builder.admin;
		this.follows = builder.follows;
	}

	public String getName() {
		return name;
	}

	public boolean isVip() {
		return vip;
	}

	public boolean isAdmin() {
		return admin;
	}
	
	public boolean isMuted() {
		// TODO Implement
		return false;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
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
	
	public boolean isFollowable(User user) {
		return !(user.getFollows().contains(this));
	}

	public boolean isFollowable() {
		return isFollowable;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<User> getFollows() {
		List<User> copy = new ArrayList<User>(follows.size());
		copy.addAll(follows);
		return copy;
	}

	public void followUser(User u) throws UserAlreadyFollowedException {
		if(u.equals(this)){
			throw new CantFollowMeException();
		}
		if (follows.contains(u)) {
			throw new UserAlreadyFollowedException();
		}
		follows.add(u);
		//u.followers.add(this);
	}

	public void unFollowUser(User u) throws UserNotFollowedException {
		if (!follows.contains(u)) {
			throw new UserNotFollowedException();
		}
		follows.remove(u);
		//u.followers.remove(this);
	}

	public List<Comment> getComments() {
		List<Comment> copy = new ArrayList<Comment>(comments.size());
		copy.addAll(comments);
		return copy;
	}

	public void addComment(Comment c) throws UserCantCommentException {
		if(!comments.contains(c)){
			comments.add(c);
			try{
				c.getFilm().addComment(c);
			}catch(UserCantCommentException e){
				// ... fix me please
				return ;
			}
		}
	}

	public void removeComment(Comment c) {
		if(comments.contains(c)){
			comments.remove(c);
			c.getFilm().removeComment(c);
		}
	}

	public boolean resetPassword(String answerToSecretQuestion,
			String newPassword) {
		if (!answerToSecretQuestion.equals(this.secretAnswer)) {
			return false;
		}
		this.password = newPassword;
		return true;
	}
	
	public void setMuted(boolean muted) {
		// TODO Implement
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

	public void canBeFollowed(User u) {
		isFollowable = !(u.getFollows().contains(this));
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
		private List<Comment> comments;
		private boolean admin = false;
		private List<User> follows;
	
		public Builder name(final String name) {
			this.name = name;
			return this;
		}
	
		public Builder lastname(final String lastname) {
			this.lastname = lastname;
			return this;
		}
	
		public Builder vip(final boolean vip) {
			this.vip = vip;
			return this;
		}
	
		public Builder email(final String email) {
			this.email = email;
			return this;
		}
	
		public Builder password(final String password) {
			this.password = password;
			return this;
		}
	
		public Builder birthdate(final Date birthdate) {
			this.birthdate = birthdate;
			return this;
		}
	
		public Builder secretQuestion(final String secretQuestion) {
			this.secretQuestion = secretQuestion;
			return this;
		}
	
		public Builder secretAnswer(final String secretAnswer) {
			this.secretAnswer = secretAnswer;
			return this;
		}
	
		public Builder comments(final List<Comment> comments) {
			this.comments = comments;
			return this;
		}
	
		public Builder admin(boolean admin) {
			this.admin = admin;
			return this;
		}
	
		public Builder follows(final List<User> follows) {
			this.follows = follows;
			return this;
		}
	
		public User build() {
			return new User(this);
		}
	}
}
