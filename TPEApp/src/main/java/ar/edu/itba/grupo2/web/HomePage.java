package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.web.widget.comment.FollowedCommentListItem;
import ar.edu.itba.grupo2.web.widget.film.LatestFilmsItem;
import ar.edu.itba.grupo2.web.widget.film.LatestReleasedFilmsItem;
import ar.edu.itba.grupo2.web.widget.film.TopFilmsItem;


@SuppressWarnings("serial")
public class HomePage extends BasePage {
	
	public HomePage() {
		super();
		
		loadLatestReleasedFilms();
		loadTopFilms();
		loadLatestAddedFilms();
		loadFollowedUserComments();
	}
	
	private void loadLatestReleasedFilms() {
		final IModel<List<Film>> latestReleasedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getNewests(7);
			}
		};
		
		add(new ListView<Film>("latestReleasedFilm", latestReleasedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestReleasedFilmsItem("latestReleasedFilmPanel", item.getModel(), latestReleasedFilmsModel));	
			}
		});
		
		// TODO Localize this String
		Label latestReleasedEmpty = new Label("latestReleasedEmpty", "No hay estrenos esta semana");
		
		if (!latestReleasedFilmsModel.getObject().isEmpty()) {
			latestReleasedEmpty.setVisible(false);
		}
		else {
			latestReleasedEmpty.setVisible(true);
		}
		
		add(latestReleasedEmpty);
	}
	
	private void loadTopFilms() {
		final IModel<List<Film>> topFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getTop(5);
			}
		};
		
		add(new ListView<Film>("topFilm", topFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new TopFilmsItem("topFilmPanel", item.getModel(), topFilmsModel));	
			}
		});
	}
	
	private void loadLatestAddedFilms() {
		final IModel<List<Film>> latestAddedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getLatest(5);
			}
		};
		
		add(new ListView<Film>("latestAddedFilm", latestAddedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestFilmsItem("latestAddedPanel", item.getModel(), latestAddedFilmsModel));	
			}
		});
	}
	
	private void loadFollowedUserComments() {
		IModel<List<Comment>> latestAddedFilmsModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (!session.isLoggedIn()) {
					return null;
				}
				
				return users.getLatestComments(session.getLoggedInUser());
			}
		};
		
		add(new ListView<Comment>("latestFollowedUser", latestAddedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FollowedCommentListItem("latestFollowedUserPanel", item.getModel()));	
			}
		});
	}

}
