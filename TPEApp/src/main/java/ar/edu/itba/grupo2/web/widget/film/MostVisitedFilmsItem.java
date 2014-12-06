package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class MostVisitedFilmsItem extends FilmListItem {

	public MostVisitedFilmsItem(String id, IModel<Film> film) {
		super(id, film);
		
		add(new Label("visits", new PropertyModel<Integer>(film, "visits")));
	}
}
