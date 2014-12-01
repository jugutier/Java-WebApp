package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.FilmDetailsPage;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;
import ar.edu.itba.grupo2.web.widget.user.UserRoleBadges;

@SuppressWarnings("serial")
public class FollowedCommentListItem extends Panel {

	public FollowedCommentListItem(String id, IModel<Comment> comment) {
		super(id, comment);
		
		CompoundPropertyModel<Comment> compoundModel = new CompoundPropertyModel<Comment>(comment);
		setDefaultModel(compoundModel);
		
		Link<Film> film = new Link<Film>("film") {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage((Film) getDefaultModelObject()));
			}
		};
		
		add(new Label("username", compoundModel.bind("user.name")));
		add(new Label("text"));
		add(new StarScoreIndicator("scoreStars",  new PropertyModel<Integer>(comment, "filmRate")));
		add(new UserRoleBadges("roleBadges", new PropertyModel<User>(comment, "user")));
		add(film);
		film.add(new Label("filmName", compoundModel.bind("film.name")));
	}
}
