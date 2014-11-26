package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

public class FilmCommentListItem extends Panel {

	public FilmCommentListItem(String id, Comment comment) {
		super(id);
		
		Label adminTag = new Label("admin-tag", "Admin");
		Label vipTag = new Label("vip-tag", "V.I.P.");
		Link<Void> usernameLink = new Link<Void>("username-link") {

			@Override
			public void onClick() {
				// TODO Go to user's profile
			}
			
		};
		
		User user = comment.getUser();
		
		vipTag.setVisible(user.isVip());
		adminTag.setVisible(user.isAdmin());
		
		add(usernameLink);
		usernameLink.add(new Label("username", comment.getUser().getName()));
		add(new StarScoreIndicator("score-stars", comment.getFilmRate(), 5));
		add(new Label("message", comment.getText()));
		add(adminTag);
		add(vipTag);
	}

}
