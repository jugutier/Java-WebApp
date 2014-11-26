package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

@SuppressWarnings("serial")
public class TopFilmsItem extends FilmListItem {

	public TopFilmsItem(String id, Film film) {
		super(id, film);
		
		add(new StarScoreIndicator("score-stars", (int)Math.floor(film.getScore()), 5));
		add(new Label("score", String.format("%,.2f", film.getScore())));
	}

}
