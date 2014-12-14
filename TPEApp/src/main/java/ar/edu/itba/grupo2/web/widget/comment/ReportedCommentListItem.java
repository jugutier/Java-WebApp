package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.FilmDetailsPage;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;
import ar.edu.itba.grupo2.web.widget.film.FilmTitle;
import ar.edu.itba.grupo2.web.widget.user.UserRoleBadges;

@SuppressWarnings("serial")
public class ReportedCommentListItem extends Panel {

	public ReportedCommentListItem(String id, IModel<Comment> comment) {
		super(id, comment);
		
		Link<Film> filmLink = new Link<Film>("filmLink", new PropertyModel<Film>(comment, "film")){

			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(getModelObject()));
			}
			
		};
		
		CommentActionsButton actionsButton = new CommentActionsButton("actionsButton", comment);
		
		filmLink.add(new FilmTitle("filmName", new PropertyModel<Film>(comment, "film")));
		
		add(actionsButton);
		add(filmLink);
		add(new Label("message", new PropertyModel<String>(comment, "text")));
		add(new Label("username", new PropertyModel<String>(comment, "user.name")));
		add(new StarScoreIndicator("scoreStars", new PropertyModel<Integer>(comment, "filmRate")));
		add(new Label("reportCount", new PropertyModel<String>(comment, "reportCount")));
		add(new UserRoleBadges("roleBadges", new PropertyModel<User>(comment, "user")));
	}
	
	public Comment comment() {
		return (Comment) getDefaultModelObject();
	}
}
