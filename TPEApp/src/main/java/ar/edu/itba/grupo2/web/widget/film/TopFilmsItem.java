package ar.edu.itba.grupo2.web.widget.film;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

@SuppressWarnings("serial")
public class TopFilmsItem extends FilmListItem {

	public TopFilmsItem(String id, final IModel<Film> film, final IModel<List<Film>> listModel) {
		super(id, film, listModel);
		
		add(new StarScoreIndicator("scoreStars", new PropertyModel<Integer>(film, "score")));
		add(new Label("score"));
	}

}
