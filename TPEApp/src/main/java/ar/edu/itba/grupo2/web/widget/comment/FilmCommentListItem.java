package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

public class FilmCommentListItem extends Panel {

	public FilmCommentListItem(String id, Comment comment) {
		super(id);
		
		Label adminTag = new Label("admin-tag", "Admin");
		Label vipTag = new Label("vip-tag", "V.I.P.");
		
		vipTag.setVisible(false);
		
		add(new Label("username", comment.getUser().getName()));
		add(new StarScoreIndicator("score-stars", comment.getFilmRate(), 5));
		add(adminTag);
		add(vipTag);
	}

}
