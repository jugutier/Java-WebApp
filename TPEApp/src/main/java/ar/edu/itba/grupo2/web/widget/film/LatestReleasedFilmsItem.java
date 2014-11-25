package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

@SuppressWarnings("serial")
public class LatestReleasedFilmsItem extends FilmListItem {

	public LatestReleasedFilmsItem(String id, Film film) {
		super(id, film);
		
		Link<Void> moreLink = new Link<Void>("more") {
			@Override
			public void onClick() {
				// TODO Go to film details
			}
		};
		Label descriptionLabel = null;
		Label noDescription = new Label("no-description", "No hay resumen disponible");
		
		String description = film.getDescription();
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
