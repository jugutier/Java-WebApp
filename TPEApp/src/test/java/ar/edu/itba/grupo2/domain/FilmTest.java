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
	 * When adding a comment film's rate should be updated.
	 * @throws UserCantCommentException 
	 */
	@Test
	public void rateUpdatesTest() throws UserCantCommentException {
		User nonVip = new User.Builder().email("a@a.com").vip(false).build();
		User vip = new User.Builder().email("b@bebe.com").vip(true).build();
		Calendar cal =  Calendar.getInstance();cal.set(2013, 03, 31);
		Film filmst  = new Film.Builder().releaseDate(cal.getTime()).build();
		
		Comment c = new Comment.Builder().user(nonVip).text("This is a nice comment").film(filmst).rate(1).build();
		filmst.addComment(c);
		Comment c2 = new Comment.Builder().film(filmst).text("This is another nice comment").user(vip).rate(3).build();
		filmst.addComment(c2);
		Assert.assertEquals(2.0, filmst.getScore());
		
	}
	/**
	 * A user cant comment twice
	 * @throws UserCantCommentException 
	 */
	@Test(expected= UserCantCommentException.class)
	public void userCantCommentTest1() throws UserCantCommentException {
		User nonVipStub = new User.Builder().email("a@a.com").vip(false).build();
		Film filmStub  = new Film.Builder().build();
		Comment c = new Comment.Builder().text("This is a nice comment").user(nonVipStub).film(filmStub).rate(2).build();
		filmStub.addComment(c);
		filmStub.addComment(c);
	}
	/**
	 * A non-VIP user cant comment an unReleasedFilm
	 */
	@Test(expected = UserCantCommentException.class)
	public void userCantCommentTest2() throws UserCantCommentException{
		User nonVip = new User.Builder().email("nonVip@a.com").vip(false).build();
		Calendar cal =  Calendar.getInstance();cal.set(2016, 05, 18);
		Film unreleasedFilm = new Film.Builder().releaseDate(cal.getTime()).build();
		Comment acceptedComment = new Comment.Builder().text("Non vip commentor").user(nonVip).rate(3).build();
		unreleasedFilm.addComment(acceptedComment);
	}
	/**
	 * A VIP user can comment an unReleasedFilm
	 */
	@Test
	public void userCanCommentTest1() throws UserCantCommentException{
		User vipUser = new User.Builder().email("vip@a.com").vip(true).build();
		Calendar cal =  Calendar.getInstance();cal.set(2016, 05, 18);
		Film unreleasedFilm2 = new Film.Builder().releaseDate(cal.getTime()).build();
		Comment goodComment = new Comment.Builder().text("Vip commentor").user(vipUser).rate(3).film(unreleasedFilm2).build();	
		unreleasedFilm2.addComment(goodComment);
		Assert.assertTrue(unreleasedFilm2.getComments().contains(goodComment));
		Assert.assertEquals(goodComment.getFilm(),unreleasedFilm2);
		
		
	}
}
