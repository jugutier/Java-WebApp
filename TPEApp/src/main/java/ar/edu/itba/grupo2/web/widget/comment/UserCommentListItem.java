package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;
import ar.edu.itba.grupo2.web.widget.FriendlyDate;
import ar.edu.itba.grupo2.web.widget.film.FilmTitle;

@SuppressWarnings("serial")
public class UserCommentListItem extends Panel {

	public UserCommentListItem(String id, IModel<Comment> comment) {
		super(id, comment);
		
		CompoundPropertyModel<Comment> compoundModel = new CompoundPropertyModel<Comment>(comment);
		setDefaultModel(compoundModel);
		
		Link<Film> film = new Link<Film>("film") {

			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage((Film) getDefaultModelObject()));
			}
			
		};
		
		add(new FriendlyDate("creationDate"));
		add(new Label("text"));
		add(film);
		film.add(new FilmTitle("filmName", new PropertyModel<Film>(compoundModel, "film")));
	}
	
}
