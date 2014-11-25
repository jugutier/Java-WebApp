package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class FilmListItem extends Panel {

	public FilmListItem(String id, Film film) {
		super(id);
		
		Link<Void> titleLink = new Link<Void>("title-link") {
			@Override
			public void onClick() {
				// TODO Go to film details
			}
		};
		
		add(titleLink);
		
		titleLink.add(new Label("title-label", film.getName()));
	}

}
