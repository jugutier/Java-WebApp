package ar.edu.itba.grupo2.web.widget.film;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;

import ar.edu.itba.grupo2.domain.film.Film;

public class NormalFilmListItem extends FilmListItem {

	public NormalFilmListItem(String id, Film film) {
		super(id, film);
		
		Date releaseDate = film.getReleaseDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = simpleDateFormat.format(releaseDate);
		
		add(new Label("release-date", strDate));
		add(new Label("director", film.getDirector()));
	}

}
