package ar.edu.itba.grupo2.web.widget.film;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class LatestFilmsItem extends FilmListItem {

	public LatestFilmsItem(String id, Film film) {
		super(id, film);
		
		Date creationDate = film.getCreationDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = simpleDateFormat.format(creationDate);
		
		add(new Label("date", strDate));
		add(new Label("comments", String.valueOf(film.getComments().size())));
	}

}
