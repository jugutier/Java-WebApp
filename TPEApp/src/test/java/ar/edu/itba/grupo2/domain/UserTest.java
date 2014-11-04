package ar.edu.itba.grupo2.domain;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import ar.edu.itba.grupo2.domain.user.UserNotFollowedException;

public class UserTest {
	/**
	 * Can't unfollow a user not being followed
	 */
	@Test(expected = UserNotFollowedException.class)
	public void unfollowTest() {
		fail("Not yet implemented");
	}
	/**
	 * Can't reset password without knowing the secret question
	 */
	@Test
	public void resetPasswordTest() {
		Assert.assertFalse(false);
	}
}
