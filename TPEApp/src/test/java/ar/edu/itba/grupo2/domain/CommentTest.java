package ar.edu.itba.grupo2.domain;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import ar.edu.itba.grupo2.domain.comment.Comment;

public class CommentTest {
	/**
	 * Comments can't be empty
	 */
	@Test(expected = IllegalArgumentException.class)
	public void emptyCommentTest() {
		new Comment.Builder()
		.film(null)
		.user(null)
		.text("")
		.rate(1)
		.build();
	}

	/**
	 * Comments can't be longer than 140 chars
	 * @generated 140 char string by random.org
	 */
	@Test(expected = IllegalArgumentException.class)
	public void tooLongCommentTest() {
		new Comment.Builder()
		.film(null)
		.user(null)
		.text("F7n5H2PeNW1QhwmVRj9c"
				+ "ZZB49MiySbOKbV0Yyk0j"
				+ "HvK3WefCTptMGiCNTxMC"
				+ "XU1XPDWIVrZZ4wraiJ0O"
				+ "gg8PSCfYnWSMqEeXjrNp"
				+ "b6DA2s8FOeaDc1QLl4vQ"
				+ "Lmmoe7h1zm046Y19XeFT"
				+ "1")
		.rate(1)
		.build();
	}

	/**
	 * Default creation date should be today
	 */
	@Test
	public void creationDateCommentTest() {
		Comment testComment = new Comment.Builder()
				.text("hi!")
				.build();
		//NOW
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    
	    //COMMENT
	    Calendar commentCal = Calendar.getInstance();
	    commentCal.setTime(testComment.getCreationDate());
	    int cYear = commentCal.get(Calendar.YEAR);
	    int cMonth = commentCal.get(Calendar.MONTH);
	    int cDay = commentCal.get(Calendar.DAY_OF_MONTH);
	    Assert.assertEquals(year, cYear);
		Assert.assertEquals(month, cMonth);
		Assert.assertEquals(day, cDay);
	}

	@Test
	public void okCommentTest() {
		new Comment.Builder()
		.text("F7n5H2PeNW1QhwmVRj9c"
				+ "ZZB49MiySbOKbV0Yyk0j"
				+ "HvK3WefCTptMGiCNTxMC"
				+ "XU1XPDWIVrZZ4wraiJ0O"
				+ "gg8PSCfYnWSMqEeXjrNp"
				+ "b6DA2s8FOeaDc1QLl4vQ"
				+ "Lmmoe7h1zm046Y19XeFT")
		.build();
	}

}
