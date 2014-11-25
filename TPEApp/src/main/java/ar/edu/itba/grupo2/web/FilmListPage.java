package ar.edu.itba.grupo2.web;

import java.util.ArrayList;
import java.util.Date;
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
				// TODO Use FilmRepo to get film list here
				List<Film> filmList = new ArrayList<Film>();
				Film f1 = new Film.Builder().name("koko").director("jajxj").build();
				Film f2 = new Film.Builder().name("jejeej").build();
				Film f3 = new Film.Builder().name("pipipi").build();
				filmList.add(f1);
				filmList.add(f2);
				filmList.add(f3);
				filmList.add(f1);
				filmList.add(f2);
				filmList.add(f3);
				filmList.add(f1);
				filmList.add(f2);
				filmList.add(f3);
				return filmList;
			}
		};
		
		add(new ListView<Film>("film-list", filmListModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new NormalFilmListItem("film-item", item.getModelObject()));
			}
		});
	}

}
