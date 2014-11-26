package ar.edu.itba.grupo2.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.widget.comment.FilmCommentListItem;

public class FilmDetailsPage extends BasePage {
	
	public FilmDetailsPage(final Film film) {
		super();
		
		Date releaseDate = film.getReleaseDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = simpleDateFormat.format(releaseDate);
		
		add(new Label("release-date", strDate));
		add(new Label("title", film.getName()));
		add(new Label("director", film.getDirector()));
		add(new Label("description", film.getDescription()));
		add(new Label("length", String.valueOf(film.getLength())));
		
		loadGenreList(film);
		loadCommentList(film);
	}
	
	private void loadGenreList(final Film film) {
		RepeatingView genreList = new RepeatingView("genre-list");
		Label genreSingular = new Label("genre-singular", "Género:");
		Label genrePlural = new Label("genre-plural", "Géneros:");
		
		List<Genre> genres = film.getGenres();
		
		
		if (genres.size() < 1) {
			genreList.setVisible(false);
			genreSingular.setVisible(false);
			genrePlural.setVisible(false);
		}
		else if (genres.size() == 1 ) {
			genreList.setVisible(true);
			genreSingular.setVisible(true);
			genrePlural.setVisible(false);
		}
		else {
			genreList.setVisible(true);
			genreSingular.setVisible(false);
			genrePlural.setVisible(true);
		}
		
		for (int i = 0; i < genres.size(); i++) {
			String result = genres.get(i).getGenre();
			
			if (i != 0) {
				result = " | " + result;
			}
			
			genreList.add(new Label(genreList.newChildId(), result));
		}
		
		add(genreList);
		add(genreSingular);
		add(genrePlural);
	}
	
	private void loadCommentList(final Film film) {
		
		final int filmId = film.getId();
		
		IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				// TODO Ask if this is the right way
				return films.get(filmId).getComments();
			}
		};
		
		WebMarkupContainer commentListContainer = new WebMarkupContainer("comment-list-container");
		
		ListView<Comment> commentListView = new ListView<Comment>("comment-list", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FilmCommentListItem("comment-list-item", item.getModelObject()));	
			}
		};
		
		// Hide comments section if there isn't any
		if (commentModel.getObject() == null || commentModel.getObject().isEmpty()) {
			commentListContainer.setVisible(false);
		}
		
		commentListContainer.add(commentListView);
		add(commentListContainer);
	}
}
