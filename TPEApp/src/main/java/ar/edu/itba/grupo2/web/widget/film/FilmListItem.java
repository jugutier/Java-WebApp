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

	public FilmListItem(String id, final IModel<Film> film) {
		super(id, film);
		
		Link<Film> titleLink = new Link<Film>("title-link", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
		};
		
		Link<Void> deleteFilm = new Link<Void>("delete-button") {
			
			@Override
			public void onClick() {
				films.delete(film.getObject());
			}
		};
		
		add(titleLink);
		add(deleteFilm);
		
		titleLink.add(new Label("title-label", film.getObject().getName()));
	}
	
}
