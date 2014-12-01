package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.widget.comment.UserCommentListItem;
import ar.edu.itba.grupo2.web.widget.user.UserRoleBadges;

@SuppressWarnings("serial")
public class ProfilePage extends BasePage {

	public ProfilePage(final User user) {
		super();
		
		CompoundPropertyModel<User> compoundModel = new CompoundPropertyModel<User>(new EntityModel<User>(User.class, user));
		setDefaultModel(compoundModel);
		
		IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return user().getComments();
			}
		};
		
		PropertyListView<Comment> commentListView = new PropertyListView<Comment>("comment", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new UserCommentListItem("commentPanel", item.getModel()));
			}
		};
		
		WebMarkupContainer commentSection = new WebMarkupContainer("commentSection") {
			@Override
			public boolean isVisible() {
				return !user().getComments().isEmpty();
			}
		};
		
		Link<User> follow = new Link<User>("follow", compoundModel) {
			
			@Override
			public void onClick() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (session.isLoggedIn())
					session.getLoggedInUser().followUser(user());
			};
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !user().equals(session.getLoggedInUser()) && user().isFollowable(session.getLoggedInUser());
			}
		};
		
		Link<User> unfollow = new Link<User>("unfollow", compoundModel) {
			
			@Override
			public void onClick() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (session.isLoggedIn())
					session.getLoggedInUser().unFollowUser(user());
			};
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !user().equals(session.getLoggedInUser()) && !user().isFollowable(session.getLoggedInUser());
			}
		};
		
		add(new Label("name"));
		add(new Label("lastname"));
		add(new Label("email"));
		add(new Label("birthdate"));
		add(new UserRoleBadges("roleBadges", compoundModel));
		add(follow);
		add(unfollow);
		add(commentSection);
		follow.add(new Label("nameFollow", compoundModel.bind("name")));
		unfollow.add(new Label("nameUnfollow", compoundModel.bind("name")));
		commentSection.add(commentListView);
	}
	
	private User user() {
		return (User) getDefaultModelObject();
	}
}
