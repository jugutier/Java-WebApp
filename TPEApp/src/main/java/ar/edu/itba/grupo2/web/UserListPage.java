package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.widget.user.UserListItem;

@SuppressWarnings("serial")
public class UserListPage extends BasePage {

	public UserListPage() {
		super();
		
		IModel<List<User>> userModel = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return users.getAll();
			}
		};
		
		PropertyListView<User> userListView = new PropertyListView<User>("user", userModel) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new UserListItem("userPanel", item.getModel()));
			}
		};
		
		add(userListView);
	}
}
