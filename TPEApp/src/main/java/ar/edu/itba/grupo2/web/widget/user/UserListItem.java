package ar.edu.itba.grupo2.web.widget.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.ProfilePage;

@SuppressWarnings("serial")
public class UserListItem extends Panel {

	public UserListItem(String id, IModel<User> user) {
		super(id, user);
		
		CompoundPropertyModel<User> compoundModel = new CompoundPropertyModel<User>(user);
		setDefaultModel(compoundModel);
		
		Link<User> profile = new Link<User>("profile", compoundModel) {
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage((User) getDefaultModelObject()));
			}
		};
		
		profile.add(new Label("name"));
		profile.add(new Label("lastname"));
		add(profile);
		add(new Label("email"));
		add(new UserRoleBadges("roleBadges", compoundModel));
	}
}
