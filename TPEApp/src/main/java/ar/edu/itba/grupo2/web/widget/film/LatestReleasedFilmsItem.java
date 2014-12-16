package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.FilmDetailsPage;

@SuppressWarnings("serial")
public class LatestReleasedFilmsItem extends FilmListItem {
	
	public LatestReleasedFilmsItem(String id, final IModel<Film> film) {
		super(id, film);
		
		Link<Film> moreLink = new Link<Film>("more", film) {
			@Override
			public void onClick() {
				setResponsePage(new FilmDetailsPage(this.getModelObject()));
			}
			
			@Override
			public boolean isVisible() {
				return film().getDescription().length() > 300;
			}
		};
		
		Label description = new Label("description", new AbstractReadOnlyModel<String>() {
			@Override
			public String getObject() {
				String ret = film().getDescription();
				
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
