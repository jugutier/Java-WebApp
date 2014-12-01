package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.widget.film.NormalFilmListItem;

@SuppressWarnings("serial")
public class FilmListPage extends BasePage {
	
	private transient Genre genre;
	private transient String director;
	
	public FilmListPage() {
		super();
		
		loadFilterForm();
		loadFilmList();
	}
	
	private void loadFilterForm() {
		Form<FilmListPage> form = new Form<FilmListPage>("form", new CompoundPropertyModel<FilmListPage>(this)) {
			@Override
			protected void onSubmit() {
				GAJAmdbSession session = GAJAmdbSession.get();
				
				if (!session.isLoggedIn()) {
					director = null;
				}
			}
		};
		
		TextField<String> directorText = new TextField<String>("director") {
			@Override
			public boolean isVisible() {
				return GAJAmdbSession.get().isLoggedIn();
			}
		};
		
		DropDownChoice<Genre> genreDropDown = new DropDownChoice<Genre>("genre", new LoadableDetachableModel<List<Genre>>() {
			
			@Override
			protected List<Genre> load() {
				return films.getGenres();
			}
		});
		
		genreDropDown.setNullValid(true);
		
		form.add(directorText);
		form.add(genreDropDown);
		add(form);
	}
	
	private void loadFilmList() {
		final IModel<List<Film>> filmListModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getFiltered(genre, director);
			}
		};
		
		add(new ListView<Film>("film", filmListModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new NormalFilmListItem("filmPanel", item.getModel(), filmListModel));
			}
		});
	}

}
