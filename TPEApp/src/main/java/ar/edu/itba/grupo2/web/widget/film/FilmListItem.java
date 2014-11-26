package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

@SuppressWarnings("serial")
public class FilmListItem extends Panel {
	
	@SpringBean
	private FilmRepo films;

	public FilmListItem(String id, final Film film) {
		super(id);
		
		final int filmId = film.getId();
		
		IModel<Film> filmModel = new LoadableDetachableModel<Film>() {
			@Override
			protected Film load() {
				return films.get(filmId); 
			}
		};
		
		Link<Film> titleLink = new Link<Film>("title-link", filmModel) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(getModelObject()));
			}
		};
		
		add(titleLink);
		
		titleLink.add(new Label("title-label", film.getName()));
	}
	
	static Film test() {
		return new Film.Builder().name("Peli").director("Alguien").description("No se").build();
	}

}
