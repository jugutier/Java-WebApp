package ar.edu.itba.grupo2.web.widget.user;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.GAJAmdbSession;

@SuppressWarnings("serial")
public class UserRoleBadges extends Panel {

	public UserRoleBadges(String id, IModel<User> user) {
		super(id, user);
		
		WebMarkupContainer adminBadge = new WebMarkupContainer("adminBadge") {
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		WebMarkupContainer vipBadge = new WebMarkupContainer("vipBadge") {
			@Override
			public boolean isVisible() {
				return user().isVip();
			}
		};
		
		WebMarkupContainer followedBadge = new WebMarkupContainer("followedBadge") {
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !user().isFollowable(session.getLoggedInUser());
			}
		};
		
		WebMarkupContainer mutedBadge = new WebMarkupContainer("mutedBadge") {
			@Override
			public boolean isVisible() {
				return user().isMuted();
			}
		};
		
		add(adminBadge);
		add(vipBadge);
		add(followedBadge);
		add(mutedBadge);
	}
	
	private User user() {
		return (User) getDefaultModelObject();
	}

}
