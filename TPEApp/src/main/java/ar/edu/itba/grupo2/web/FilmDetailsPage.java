package ar.edu.itba.grupo2.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.widget.comment.FilmCommentListItem;
import ar.edu.itba.grupo2.web.widget.film.TopFilmsItem;

public class FilmDetailsPage extends BasePage {
	
	public FilmDetailsPage(Film film) {
		super();
		
		film = new Film.Builder().name("koko").director("jajxj").releaseDate(new Date()).length(45).description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vulputate, sem ut porta mollis, arcu risus eleifend risus, a tempus libero purus in risus. Duis facilisis, diam sit amet luctus elementum, lorem odio aliquam augue, sit amet mattis magna eros nec sapien. Fusce at nibh eget mauris viverra fermentum. Pellentesque nulla velit, ultrices non commodo ut, finibus in nibh. Nam ullamcorper massa congue magna fringilla, eu venenatis diam mollis. Vestibulum at vestibulum massa, et sollicitudin neque. Fusce felis nisi, cursus ultrices gravida vitae, mattis quis lacus. Suspendisse at commodo mi, id posuere dolor. Mauris vel mi diam. In consectetur leo magna, quis ornare dui pulvinar at. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. ").build();
		
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
		// TODO Get real genres here
		RepeatingView genreList = new RepeatingView("genre-list");
		Label genreSingular = new Label("genre-singular", "Género:");
		Label genrePlural = new Label("genre-plural", "Géneros:");
		ArrayList<String> genres = new ArrayList<String>();
		
		
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
			String result = genres.get(i);
			
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
				// TODO Use given Film to get comment list here
				ArrayList<Comment> commentList = new ArrayList<Comment>();
				User user = new User.Builder().admin(true).name("pepito").email("a@a.com").build();
				Comment comment = new Comment.Builder().text("pepepepepepe").rate(3).text("kakaskksakask").user(user).build();
				
				commentList.add(comment);
				commentList.add(comment);
				commentList.add(comment);
				
				return commentList;
				
				//return film.getComments();
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
