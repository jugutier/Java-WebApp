package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.widget.film.LatestFilmsItem;
import ar.edu.itba.grupo2.web.widget.film.LatestReleasedFilmsItem;
import ar.edu.itba.grupo2.web.widget.film.TopFilmsItem;


public class HomePage extends BasePage {
	
	public HomePage() {
		super();
		loadLatestReleasedFilms();		
		loadTopFilms();
		loadLatestAddedFilms();
	}
	
	private void loadLatestReleasedFilms() {
		IModel<List<Film>> latestReleasedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getNewests(7);
			}
		};
		
		add(new ListView<Film>("latest-released-list", latestReleasedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestReleasedFilmsItem("latest-released-item", item.getModel()));	
			}
		});
		
		// TODO Localize this String
		Label latestReleasedEmpty = new Label("latest-released-empty", "No hay estrenos esta semana");
		
		if (!latestReleasedFilmsModel.getObject().isEmpty()) {
			latestReleasedEmpty.setVisible(false);
		}
		else {
			latestReleasedEmpty.setVisible(true);
		}
		
		add(latestReleasedEmpty);
	}
	
	private void loadTopFilms() {
		IModel<List<Film>> topFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getTop(5);
			}
		};
		
		add(new ListView<Film>("top-films-list", topFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new TopFilmsItem("top-films-item", item.getModel()));	
			}
		});
	}
	
	private void loadLatestAddedFilms() {
		IModel<List<Film>> latestAddedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getLatest(5);
			}
		};
		
		add(new ListView<Film>("latest-list", latestAddedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestFilmsItem("latest-item", item.getModel()));	
			}
		});
	}

}
