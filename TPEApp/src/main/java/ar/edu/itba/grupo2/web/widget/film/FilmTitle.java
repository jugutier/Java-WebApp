package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class FilmTitle extends Panel {

	public FilmTitle(String id, IModel<Film> film) {
		super(id, film);
		
		add(new Label("name", new PropertyModel<Film>(film, "name")));
		
		add(new WebMarkupContainer("icon") {
			@Override
			public boolean isVisible() {
				return (int)film().getScore() >= 4;
			}
		});
	}
	
	public Film film() {
		return (Film) getDefaultModelObject();
	}
}
