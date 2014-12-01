package ar.edu.itba.grupo2.web.widget.film;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class LatestFilmsItem extends FilmListItem {

	@SuppressWarnings("unchecked")
	public LatestFilmsItem(String id, IModel<Film> film, final IModel<List<Film>> listModel) {
		super(id, film, listModel);
		
		CompoundPropertyModel<Film> compoundModel = (CompoundPropertyModel<Film>) getDefaultModel();
		
		add(new Label("creationDate"));
		add(new Label("comments", compoundModel.bind("comments.size")));
	}

}
