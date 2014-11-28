package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

@SuppressWarnings("serial")
public class LatestReleasedFilmsItem extends FilmListItem {
	
	@SpringBean
	private FilmRepo films;

	public LatestReleasedFilmsItem(String id, final IModel<Film> film) {
		super(id, film);
		
		Link<Film> moreLink = new Link<Film>("more", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
		};
		
		Label descriptionLabel = null;
		Label noDescription = new Label("no-description", "No hay resumen disponible");
		
		String description = film.getObject().getDescription();
		boolean emptyDescription = false;
		
		// If the Film has no description, display a proper message
		if (ValidationUtilities.paramEmpty(description)) {
			emptyDescription = true;
			moreLink.setVisible(false);
		}
		else {
			if (description.length() > 300) {
				description = description.substring(0, 300) + "...";
				moreLink.setVisible(true);
			}
			else {
				moreLink.setVisible(false);
			}
		}		

		descriptionLabel = new Label("description", description);
		
		descriptionLabel.setVisible(!emptyDescription);
		noDescription.setVisible(emptyDescription);
		
		add(descriptionLabel);
		add(moreLink);
		add(noDescription);		
	}

}
