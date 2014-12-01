package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

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
		
		add(new Label("creationDate"));
		add(new Label("text"));
		add(film);
		film.add(new Label("filmName", compoundModel.bind("film.name")));
	}
	
}