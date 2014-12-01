package ar.edu.itba.grupo2.web.widget.film;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

@SuppressWarnings("serial")
public class LatestReleasedFilmsItem extends FilmListItem {
	
	public LatestReleasedFilmsItem(String id, final IModel<Film> film, final IModel<List<Film>> listModel) {
		super(id, film, listModel);
		
		Link<Film> moreLink = new Link<Film>("more", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
		};
		
		// TODO Trim film description to 300 chars max.
		Label description = new Label("description");
		
		add(description);
		add(moreLink);
	}

}
