package ar.edu.itba.grupo2.web.widget.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.user.User;

@SuppressWarnings("serial")
public class UserRoleBadges extends Panel {

	public UserRoleBadges(String id, IModel<User> user) {
		super(id, user);
		
		// TODO Localize
		Label adminBadge = new Label("adminBadge", "Admin") {
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		Label vipBadge = new Label("vipBadge", "V.I.P.") {
			@Override
			public boolean isVisible() {
				return user().isVip();
			}
		};
		
		add(adminBadge);
		add(vipBadge);
		
	}
	
	private User user() {
		return (User) getDefaultModelObject();
	}

}
