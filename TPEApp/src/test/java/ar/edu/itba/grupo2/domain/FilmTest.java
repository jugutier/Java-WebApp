package ar.edu.itba.grupo2.domain;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;
import ar.edu.itba.grupo2.domain.user.User;

public class FilmTest {
	
	/**
	 * A user cant comment twice
	 * @throws UserCantCommentException 
	 */
	@Test(expected= UserCantCommentException.class)
	public void userCantCommentTest1() throws UserCantCommentException {
		User nonVipStub = new User.Builder().id(1).email("a@a.com").vip(false).build();
		Film filmStub  = new Film.Builder().build();
		Comment c = new Comment.Builder().text("This is a nice comment").user(nonVipStub).rate(2).build();
		filmStub.addComment(c);
		filmStub.addComment(c);
	}
	/**
	 * A non-VIP user cant comment an unReleasedFilm
	 */
	@Test
	public void userCantCommentTest2() {
	}
	/**
	 * A VIP user can comment an unReleasedFilm
	 */
	@Test
	public void userCanCommentTest1() {
	}
	/**
	 * When adding a comment film's rate should be updated.
	 * @throws UserCantCommentException 
	 */
	@Test
	public void rateUpdatesTest() throws UserCantCommentException {
		User nonVipStub = new User.Builder().id(1).email("a@a.com").vip(false).build();
		User vipStub = new User.Builder().id(2).email("b@bebe.com").vip(true).build();
		Calendar cal =  Calendar.getInstance();cal.set(1991, 03, 31);
		Film filmStub  = new Film.Builder().creationDate(cal.getTime()).build();
		
		Comment c = new Comment.Builder().text("This is a nice comment").user(nonVipStub).rate(1).build();
		
		filmStub.addComment(c);
		Comment c2 = new Comment.Builder().text("This is another nice comment").user(vipStub).rate(3).build();
		filmStub.addComment(c2);
		Assert.assertEquals(2.0, filmStub.getScore());
		
	}
}
