package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
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
		
		loadWelcomeSection();
		loadLatestReleasedFilms();
		loadTopFilms();
		loadLatestAddedFilms();
		loadMostVisitedFilms();
		loadFollowedUserComments();
	}
	
	private void loadWelcomeSection() {
		WebMarkupContainer guestUserContainer = new WebMarkupContainer("guestUserContainer") {
			@Override
			public boolean isVisible() {
				return !GAJAmdbSession.get().isLoggedIn();
			}
		};
		
		Link<Void> register = new Link<Void>("register") {
			@Override
			public void onClick() {
				// TODO Go to register page
				
			}
		};
		
		guestUserContainer.add(register);
		add(guestUserContainer);
	}
	
	private void loadLatestReleasedFilms() {
		final IModel<List<Film>> latestReleasedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				return films.getNewests(7);
			}
		};
		
		WebMarkupContainer latestReleasedFilmsContainer = new WebMarkupContainer("latestReleasedFilmsContainer") {
			@Override
			public boolean isVisible() {
				return !latestReleasedFilmsModel.getObject().isEmpty();
			}
		};
		
		latestReleasedFilmsContainer.add(new ListView<Film>("latestReleasedFilm", latestReleasedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestReleasedFilmsItem("latestReleasedFilmPanel", item.getModel(), latestReleasedFilmsModel));	
			}
		});
		
		add(latestReleasedFilmsContainer);
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
	
	private void loadMostVisitedFilms() {
		final IModel<List<Film>> mostVisitedFilmsModel = new LoadableDetachableModel<List<Film>>() {
			@Override
			protected List<Film> load() {
				// TODO Replace with most visited films
				return films.getLatest(5);
			}
		};
		
		add(new ListView<Film>("mostVisitedFilm", mostVisitedFilmsModel) {
			@Override
			protected void populateItem(ListItem<Film> item) {
				item.add(new LatestFilmsItem("mostVisitedPanel", item.getModel(), mostVisitedFilmsModel));	
			}
		});
	}
	
	private void loadFollowedUserComments() {
		WebMarkupContainer latestFollowedUserContainer = new WebMarkupContainer("latestFollowedUserContainer") {
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !users.getLatestComments(session.getLoggedInUser()).isEmpty();
			}
		};
		
		IModel<List<Comment>> latestFollowedUserModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (!session.isLoggedIn()) {
					return null;
				}
				
				return users.getLatestComments(session.getLoggedInUser());
			}
		};
		
		latestFollowedUserContainer.add(new ListView<Comment>("latestFollowedUser", latestFollowedUserModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FollowedCommentListItem("latestFollowedUserPanel", item.getModel()));	
			}
		});
		
		add(latestFollowedUserContainer);
	}

}
