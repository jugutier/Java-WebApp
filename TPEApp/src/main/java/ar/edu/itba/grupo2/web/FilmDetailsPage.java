package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.widget.comment.CommentForm;
import ar.edu.itba.grupo2.web.widget.comment.FilmCommentListItem;

@SuppressWarnings("serial")
public class FilmDetailsPage extends BasePage {
	
	private WebMarkupContainer filmDetailsContainer;
	
	public FilmDetailsPage(final Film film) {
		super();
		
		setDefaultModel(new CompoundPropertyModel<Film>(new EntityModel<Film>(Film.class, film)));
		
		filmDetailsContainer = new WebMarkupContainer("filmDetailsContainer");

		add(new Label("name"));
		filmDetailsContainer.add(new Label("releaseDate"));
		filmDetailsContainer.add(new Label("director"));
		filmDetailsContainer.add(new Label("description"));
		filmDetailsContainer.add(new Label("length"));
		
		loadGenreList();
		loadCommentList();
		addCommentForm();
		
		add(filmDetailsContainer);
	}
	
	private void loadGenreList() {
		// TODO Implement model here
		RepeatingView genreList = new RepeatingView("genreList");
		Label genreSingular = new Label("genreSingular", "Género:");
		Label genrePlural = new Label("genrePlural", "Géneros:");
		
		List<Genre> genres = film().getGenres();
		
		
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
		
		filmDetailsContainer.add(genreList);
		filmDetailsContainer.add(genreSingular);
		filmDetailsContainer.add(genrePlural);
	}
	
	private void loadCommentList() {
		
		final IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return film().getCommentsForUser(GAJAmdbSession.get().getLoggedInUser());
			}
		};
		
		WebMarkupContainer commentListContainer = new WebMarkupContainer("commentListContainer") {
			@Override
			public boolean isVisible() {
				return !film().getComments().isEmpty();
			}
		};
		
		ListView<Comment> commentListView = new ListView<Comment>("commentList", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FilmCommentListItem("commentListItem", item.getModel(), commentModel));	
			}
		};
		
		commentListContainer.add(commentListView);
		add(commentListContainer);
	}
	
	@SuppressWarnings("unchecked")
	private void addCommentForm() {
		add(new CommentForm("commentForm", (IModel<Film>)getDefaultModel()));
		
		// Change the style of the details frame, depending if the user can comment or not
		filmDetailsContainer.add(new AttributeModifier("class", new Model<String>() {
			@Override
			public String getObject() {
				GAJAmdbSession session = GAJAmdbSession.get();
				String frameType = "item-container";
				
				if (session.isLoggedIn() && film().userCanComment(session.getLoggedInUser())) {
					frameType = "comment-body";
				}
				
				return frameType;
			}
		}));
		
	}
	
	private Film film() {
		return (Film) getDefaultModelObject();
	}
}
