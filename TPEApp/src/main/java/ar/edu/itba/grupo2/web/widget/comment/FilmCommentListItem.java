package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.ProfilePage;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;
import ar.edu.itba.grupo2.web.widget.user.UserRoleBadges;

@SuppressWarnings("serial")
public class FilmCommentListItem extends Panel {

	@SpringBean
	private UserRepo users;
	
	public FilmCommentListItem(String id, IModel<Comment> comment) {
		super(id, comment);
		
		final CompoundPropertyModel<Comment> compoundModel;
		setDefaultModel(compoundModel = new CompoundPropertyModel<Comment>(comment));
		
		Label commentRating = new Label("rate");
		
		Link<Void> usernameLink = new Link<Void>("usernameLink") {

			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(comment().getUser()));
			}
			
		};
		
		WebMarkupContainer scoreButton = new WebMarkupContainer("scoreButton") {

			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !comment().getUser().equals(session.getLoggedInUser()) && !comment().isRatedBy(session.getLoggedInUser());
			}
			
		};
		
		WebMarkupContainer scoreDisplay = new WebMarkupContainer("scoreDisplay") {
			
			@Override
			public boolean isVisible() {
				return comment().isRated();
			}
		};
		
		RepeatingView scoreItem = new RepeatingView("scoreItem");
		
		// Fill list with stars
		for (int i = 0; i < 6; i++) {
			final int index = i;
			
			StarScoreIndicator stars = new StarScoreIndicator("scoreStarsShow", new AbstractReadOnlyModel<Integer>() {
				@Override
				public Integer getObject() {
					return index;
				}
			});
			
			Label scoreNumber = new Label("scoreNumber", new AbstractReadOnlyModel<String>() {
				@Override
				public String getObject() {
					return String.valueOf(index);
				}
			}); 
			
			Link<Void> scoreLink = new Link<Void>(scoreItem.newChildId()) {

				@Override
				public void onClick() {
					comment().rate(GAJAmdbSession.get().getLoggedInUser(), index);
				}
				
			};
			
			scoreLink.add(scoreNumber);
			scoreLink.add(stars);
			scoreItem.add(scoreLink);
		}
		
		CommentActionsButton actionsButton = new CommentActionsButton("actionsButton", compoundModel);
		
		add(usernameLink);
		usernameLink.add(new Label("username", compoundModel.bind("user.name")));
		add(new StarScoreIndicator("scoreStars", new PropertyModel<Integer>(comment, "filmRate")));
		add(new Label("text"));
		add(new UserRoleBadges("roleBadges", new PropertyModel<User>(comment, "user")));
		

		scoreButton.add(scoreItem);
		
		scoreDisplay.add(commentRating);
		
		add(scoreButton);
		add(actionsButton);
		add(scoreDisplay);
	}
	
	private Comment comment() {
		return (Comment) getDefaultModelObject();
	}

}
