package ar.edu.itba.grupo2.domain;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import ar.edu.itba.grupo2.domain.user.CantCreateWithoutEmailException;
import ar.edu.itba.grupo2.domain.user.CantFollowMeException;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserNotFollowedException;

public class UserTest {
	/**
	 * Can't unfollow a user not being followed
	 */
	@Test(expected = UserNotFollowedException.class)
	public void unfollowTest() {
		User user = new User.Builder().id(1).email("a@a.com").follows(new ArrayList<User>()).build();
		User toUnFollow = new User.Builder().id(2).email("b@b.com").build();
		user.unFollowUser(toUnFollow);
	}
	/**
	 * Can't reset password without knowing the secret question
	 */
	@Test
	public void resetPasswordTest() {
		User user = new User.Builder().id(1).email("a@a.com").password("test").secretAnswer("incognito").build();
		user.resetPassword("otra que no es", "nueva pass");
		Assert.assertFalse(false);
	}
	
	@Test(expected = CantFollowMeException.class)
	public void followTest() {
		User user = new User.Builder().id(1).email("a@a.com").follows(new ArrayList<User>()).build();
		user.followUser(user);
	}
	
	@Test(expected = CantCreateWithoutEmailException.class)
	public void createUserTest(){
		User newUser = new User.Builder().id(3).build();
	}
}
