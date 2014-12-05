package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class NormalFilmListItem extends FilmListItem {

	public NormalFilmListItem(String id, final IModel<Film> film) {
		super(id, film);
		
		add(new Label("releaseDate"));
		add(new Label("director"));
	}

}
