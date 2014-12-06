package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.widget.FriendlyDate;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;
import ar.edu.itba.grupo2.web.widget.comment.CommentForm;
import ar.edu.itba.grupo2.web.widget.comment.FilmCommentListItem;
import ar.edu.itba.grupo2.web.widget.film.FilmDisplayImage;
import ar.edu.itba.grupo2.web.widget.film.FilmTitle;

@SuppressWarnings("serial")
public class FilmDetailsPage extends BasePage {

	private WebMarkupContainer filmDetailsContainer;

	public FilmDetailsPage(final Film film) {
		super();

		CompoundPropertyModel<Film> compoundModel = new CompoundPropertyModel<Film>(new EntityModel<Film>(Film.class, film));
		setDefaultModel(compoundModel);
		
		Link<Film> deleteFilm = new Link<Film>("deleteButton", compoundModel) {
			
			@Override
			public void onClick() {
				films.delete(this.getModelObject());
				setResponsePage(new FilmListPage());
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
		};
		
		Link<Film> editFilm = new Link<Film>("editButton", compoundModel) {
			
			@Override
			public void onClick() {
				setResponsePage(new EditFilmPage(film()));
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
		};

		filmDetailsContainer = new WebMarkupContainer("filmDetailsContainer");

		add(deleteFilm);
		add(editFilm);
		add(new FilmTitle("name", compoundModel));
		filmDetailsContainer.add(new StarScoreIndicator("starScore", new PropertyModel<Integer>(compoundModel, "score")));
		filmDetailsContainer.add(new FilmDisplayImage("thumbnail", compoundModel));
		filmDetailsContainer.add(new FriendlyDate("releaseDate"));
		filmDetailsContainer.add(new Label("director"));
		filmDetailsContainer.add(new Label("description"));
		filmDetailsContainer.add(new Label("length"));
		// TODO Ask if this it's ok to do ajax requests like this
		filmDetailsContainer.add(new AjaxLazyLoadPanel("stock") {
			@Override
			public Component getLazyLoadComponent(String id) {
				String string = null;
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
				query.whereEqualTo("name", film().getName());
				try {
					List<ParseObject> result = query.find();
					if (result == null) {
						// TODO Localize
						string = "Sin stock";
					}
					else {
						string = String.valueOf(query.find().get(0).getInt("stock"));
					}
				} catch (ParseException e) {
					// TODO Localize
					string = "Sin stock";
				}
				Label ret = new Label(id, string);
				return ret;
			}
		});
		filmDetailsContainer.add(new Label("visits"));
		
		loadGenreList();
		loadCommentList();
		addCommentForm();

		add(filmDetailsContainer);
	}

	private void loadGenreList() {
		Label genreLabel = new Label("genreLabel", new Model<String>() {
			@Override
			public String getObject() {
				// TODO Ask it this is the right way to return strings
				// TODO Localize
				if (film().getGenres().size() == 1) {
					return "Género";
				}
				
				return "Géneros";
			}
		});
		
		WebMarkupContainer genreContainer = new WebMarkupContainer("genreContainer") {
			@Override
			public boolean isVisible() {
				return !film().getGenres().isEmpty();
			}
		};

		IModel<List<Genre>> genreModel = new LoadableDetachableModel<List<Genre>>() {
			@Override
			protected List<Genre> load() {
				return film().getGenres();
			}
		};
		
		genreContainer.add(new ListView<Genre>("genre", genreModel) {
			@Override
			protected void populateItem(ListItem<Genre> item) {
				item.add(new Label("genreItem", item.getModel()));	
			}
		});
		
		genreContainer.add(genreLabel);
		
		filmDetailsContainer.add(genreContainer);
	}

	private void loadCommentList() {

		final IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return film().getCommentsForUser(
						GAJAmdbSession.get().getLoggedInUser());
			}
		};

		WebMarkupContainer commentListContainer = new WebMarkupContainer(
				"commentListContainer") {
			@Override
			public boolean isVisible() {
				return !film().getComments().isEmpty();
			}
		};

		ListView<Comment> commentListView = new ListView<Comment>(
				"commentList", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new FilmCommentListItem("commentListItem", new EntityModel<Comment>(Comment.class, item.getModelObject())));
			}
		};

		commentListContainer.add(commentListView);
		add(commentListContainer);
	}

	@SuppressWarnings("unchecked")
	private void addCommentForm() {
		add(new CommentForm("commentForm", (IModel<Film>) getDefaultModel()));

		// Change the style of the details frame, depending if the user can
		// comment or not
		filmDetailsContainer.add(new AttributeModifier("class",
				new Model<String>() {
					@Override
					public String getObject() {
						GAJAmdbSession session = GAJAmdbSession.get();
						String frameType = "item-container";

						if (session.isLoggedIn()
								&& film().userCanComment(
										session.getLoggedInUser())) {
							frameType = "comment-body";
						}

						return frameType;
					}
				}));

	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		film().visitFilm();
	}

	private Film film() {
		return (Film) getDefaultModelObject();
	}
}
