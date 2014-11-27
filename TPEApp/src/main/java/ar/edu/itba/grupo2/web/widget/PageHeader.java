package ar.edu.itba.grupo2.web.widget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.FilmListPage;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.HomePage;

@SuppressWarnings("serial")
public class PageHeader extends Panel {
	
	@SpringBean
	private UserRepo users;
	
	private transient String email;
	private transient String password;

	public PageHeader(String id) {
		super(id);
		
		final User user = GAJAmdbSession.get().getLoggedInUser(users);
		
		// Create links
		Link<Void> bannerLink = null;
		Link<Void> filmListLink = null;
		Link<Void> userListLink = null;
		
		bannerLink = new Link<Void>("banner") {

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
			
		};
		
		filmListLink = new Link<Void>("film-list-link") {
			
			@Override
			public void onClick() {
				setResponsePage(new FilmListPage());
			}
			
		};
		
		userListLink = new Link<Void>("user-list-link") {
			
			@Override
			public void onClick() {
				setResponsePage(new FilmListPage());
			}
			
		};
		
		// Create navbar user panels
		WebMarkupContainer loginForm = new WebMarkupContainer("login-form");
		WebMarkupContainer userPanel = new WebMarkupContainer("user-panel");
		
		// Display user list and user panel only if there is a user logged in
		// and display the login form only if the user is a guest
		userPanel.setVisible(user != null);
		userListLink.setVisible(user != null);
		loginForm.setVisible(user == null);
		
		// If there is a user logged in, display his name in the user panel
		Label usernameLabel = null;
		if (user == null) {
			usernameLabel = new Label("username", "");
		}
		else {
			usernameLabel = new Label("username", user.getName());
		}
		
		add(bannerLink);
		add(loginForm);
		add(userPanel);
		add(userListLink);
		add(filmListLink);
		
		userPanel.add(usernameLabel);
		
		Form<PageHeader> form = new Form<PageHeader>("the-form", new CompoundPropertyModel<PageHeader>(this)) {
			@Override
			protected void onSubmit() {
				GAJAmdbSession session = GAJAmdbSession.get();

				if (session.authenticate(email, password, users)) {
					if (!continueToOriginalDestination()) {
						setResponsePage(getApplication().getHomePage());
					}
				} else {
					
				}
			}
		};

		form.add(new TextField<String>("email").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("submit", Model.of("")));
		loginForm.add(form);
	}

}
