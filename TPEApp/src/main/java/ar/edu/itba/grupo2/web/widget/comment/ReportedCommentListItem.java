package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

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
		
		Link<Comment> deleteComment = new Link<Comment>("deleteButton", comment) {
			
			@Override
			public void onClick() {
				comment().remove();
			}
		};
		
		Link<Comment> discardReports = new Link<Comment>("discardReports", comment) {

			@Override
			public void onClick() {
				comment().discardReports();
			}
			
		};
		
		add(deleteComment);
		add(discardReports);
		add(filmLink);
		filmLink.add(new Label("filmName", new PropertyModel<String>(comment, "film.name")));
		
		add(new Label("message", new PropertyModel<String>(comment, "text")));
		add(new Label("username", new PropertyModel<String>(comment, "user.name")));
		add(new StarScoreIndicator("scoreStars", new PropertyModel<Integer>(comment, "filmRate")));
		add(new Label("reportCount", new PropertyModel<String>(comment, "reportCount")));
	}
	
	public Comment comment() {
		return (Comment) getDefaultModelObject();
	}
}
