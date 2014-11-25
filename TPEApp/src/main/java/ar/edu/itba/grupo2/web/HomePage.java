package ar.edu.itba.grupo2.web;

import java.util.ArrayList;
import java.util.List;

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
				// TODO Use FilmRepo to get latest releases here
				List<Film> topFilms = new ArrayList<Film>();
				Film f1 = new Film.Builder().name("pedro").description("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?").build();
				Film f2 = new Film.Builder().name("xzcas").build();
				Film f3 = new Film.Builder().name("ñañaaña").description("nuse").build();
				topFilms.add(f1);
				topFilms.add(f2);
				topFilms.add(f3);
				return topFilms;
			}
		};
		
		add(new ListView<Film>("latest-released-list", latestReleasedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestReleasedFilmsItem("latest-released-item", item.getModelObject()));	
			}
		});
	}
	
	private void loadTopFilms() {
		IModel<List<Film>> topFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				// TODO Use FilmRepo to get top 5 films here
				List<Film> topFilms = new ArrayList<Film>();
				Film f1 = new Film.Builder().name("koko").build();
				Film f2 = new Film.Builder().name("jejeej").build();
				Film f3 = new Film.Builder().name("pipipi").build();
				topFilms.add(f1);
				topFilms.add(f2);
				topFilms.add(f3);
				return topFilms;
			}
		};
		
		add(new ListView<Film>("top-films-list", topFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new TopFilmsItem("top-films-item", item.getModelObject()));	
			}
		});
	}
	
	private void loadLatestAddedFilms() {
		IModel<List<Film>> latestAddedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				// TODO Use FilmRepo to get latest added films here
				List<Film> topFilms = new ArrayList<Film>();
				Film f1 = new Film.Builder().name("xxzczxcw").build();
				Film f2 = new Film.Builder().name("412").build();
				Film f3 = new Film.Builder().name("sss").build();
				topFilms.add(f1);
				topFilms.add(f2);
				topFilms.add(f3);
				return topFilms;
			}
		};
		
		add(new ListView<Film>("latest-list", latestAddedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestFilmsItem("latest-item", item.getModelObject()));	
			}
		});
	}

}
