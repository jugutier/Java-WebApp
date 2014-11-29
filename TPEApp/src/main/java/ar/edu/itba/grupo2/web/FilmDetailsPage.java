package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.widget.comment.CommentForm;
import ar.edu.itba.grupo2.web.widget.comment.FilmCommentListItem;

@SuppressWarnings("serial")
public class FilmDetailsPage extends BasePage {
	
	public FilmDetailsPage(final Film film) {
		super();
		
		setDefaultModel(new CompoundPropertyModel<Film>(new EntityModel<Film>(Film.class, film)));
		
		add(new Label("releaseDate"));
		add(new Label("name"));
		add(new Label("director"));
		add(new Label("description"));
		add(new Label("length"));
		
		add(new CommentForm("commentForm"));
		
		loadGenreList(film);
		loadCommentList(film);
	}
	
	private void loadGenreList(final Film film) {
		RepeatingView genreList = new RepeatingView("genreList");
		Label genreSingular = new Label("genreSingular", "Género:");
		Label genrePlural = new Label("genrePlural", "Géneros:");
		
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
		
		IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return getFilm().getComments();
			}
		};
		
		WebMarkupContainer commentListContainer = new WebMarkupContainer("commentListContainer");
		
		ListView<Comment> commentListView = new ListView<Comment>("commentList", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FilmCommentListItem("commentListItem", item.getModel()));	
			}
		};
		
		// Hide comments section if there isn't any
		if (commentModel.getObject() == null || commentModel.getObject().isEmpty()) {
			commentListContainer.setVisible(false);
		}
		
		commentListContainer.add(commentListView);
		add(commentListContainer);
	}
	
	private Film getFilm() {
		return (Film) getDefaultModelObject();
	}
}
