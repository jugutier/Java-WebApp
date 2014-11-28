package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.widget.film.NormalFilmListItem;

public class FilmListPage extends BasePage {
	
	public FilmListPage() {
		super();
		
		loadFilmList();
	}
	
	private void loadFilmList() {
		IModel<List<Film>> filmListModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getAll();
			}
		};
		
		add(new ListView<Film>("film-list", filmListModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new NormalFilmListItem("film-item", item.getModel()));
			}
		});
	}

}
