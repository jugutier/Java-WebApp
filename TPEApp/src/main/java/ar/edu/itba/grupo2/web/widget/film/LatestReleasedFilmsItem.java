package ar.edu.itba.grupo2.web.widget.film;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

@SuppressWarnings("serial")
public class LatestReleasedFilmsItem extends FilmListItem {
	
	public LatestReleasedFilmsItem(String id, final IModel<Film> film, final IModel<List<Film>> listModel) {
		super(id, film, listModel);
		
		Link<Film> moreLink = new Link<Film>("more", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
			
			@Override
			public boolean isVisible() {
				// TODO Check this value
				return film().getDescription().length() > 300;
			}
		};
		
		// TODO Ask if this is the right way to intercept strings
		Label description = new Label("description", new Model<String>() {
			@Override
			public String getObject() {
				String ret = film().getDescription();
				
				// TODO Check this value
				if (ret.length() > 300) {
					ret = ret.substring(0, 300) + "...";
				}
				
				return ret;
			}
		});
		
		add(description);
		add(moreLink);
	}
	
	protected Film film() {
		return (Film) getDefaultModelObject();
	}

}
