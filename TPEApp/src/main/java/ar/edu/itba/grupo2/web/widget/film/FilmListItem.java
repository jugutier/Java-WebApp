package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.web.EditFilmPage;
import ar.edu.itba.grupo2.web.FilmDetailsPage;
import ar.edu.itba.grupo2.web.GAJAmdbSession;

@SuppressWarnings("serial")
public class FilmListItem extends Panel {
	
	@SpringBean
	private FilmRepo films;

	public FilmListItem(String id, final IModel<Film> film) {
		super(id, film);
		
		CompoundPropertyModel<Film> compoundModel = new CompoundPropertyModel<Film>(film);
		
		setDefaultModel(compoundModel);
		
		Link<Film> titleLink = new Link<Film>("nameLink", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
		};
		
		WebMarkupContainer actionsButton = new WebMarkupContainer("actionsButton") {
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
		};
		
		Link<Film> deleteFilm = new Link<Film>("deleteButton", film) {
			
			@Override
			public void onClick() {
				films.delete(this.getModelObject());
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
		};
		
		Link<Film> editFilm = new Link<Film>("editButton", film) {
			
			@Override
			public void onClick() {
				setResponsePage(new EditFilmPage((Film) getDefaultModelObject()));
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
		};
		
		add(actionsButton);
		add(titleLink);
		
		actionsButton.add(deleteFilm);
		actionsButton.add(editFilm);
		
		titleLink.add(new FilmTitle("name", compoundModel));
	}
	
}
