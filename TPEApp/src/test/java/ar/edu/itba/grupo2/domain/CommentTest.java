package ar.edu.itba.grupo2.domain;

import java.util.Date;

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
				.film(null)
				.user(null)
				.text("hi!")
				.rate(1)
				.build();
		
		Assert.assertSame(new Date(), testComment.getCreationDate());
	}

	@Test
	public void okCommentTest() {
		new Comment.Builder()
		.film(null)
		.user(null)
		.text("F7n5H2PeNW1QhwmVRj9c"
				+ "ZZB49MiySbOKbV0Yyk0j"
				+ "HvK3WefCTptMGiCNTxMC"
				+ "XU1XPDWIVrZZ4wraiJ0O"
				+ "gg8PSCfYnWSMqEeXjrNp"
				+ "b6DA2s8FOeaDc1QLl4vQ"
				+ "Lmmoe7h1zm046Y19XeFT")
		.rate(1)
		.build();
	}

}
