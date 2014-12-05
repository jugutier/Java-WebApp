package ar.edu.itba.grupo2.web.widget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.FilmListPage;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.HomePage;
import ar.edu.itba.grupo2.web.LoadFilmsPage;
import ar.edu.itba.grupo2.web.NewFilmPage;
import ar.edu.itba.grupo2.web.ProfilePage;
import ar.edu.itba.grupo2.web.RegisterPage;
import ar.edu.itba.grupo2.web.ReportedCommentsPage;
import ar.edu.itba.grupo2.web.ResetPasswordPage;
import ar.edu.itba.grupo2.web.UserListPage;

@SuppressWarnings("serial")
public class PageHeader extends Panel {
	
	@SpringBean
	private UserRepo users;
	
	private transient String email;
	private transient String password;
	
	WebMarkupContainer loginForm;
	WebMarkupContainer userPanel;
	Link<Void> userListLink;
	Label usernameLabel;
	
	IModel<User> userModel = null;

	public PageHeader(String id) {
		super(id);

		userModel = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return GAJAmdbSession.get().getLoggedInUser();
			}
		};
		
		// Create links
		Link<Void> bannerLink = null;
		Link<Void> filmListLink = null;
		Link<Void> logout = null;
		Link<Void> reportedComments = null;
		Link<Void> profile = null;
		Link<Void> addFilm = null;
		Link<Void> addCSVFilms = null;
		
		bannerLink = new Link<Void>("banner") {

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
			
		};
		
		filmListLink = new Link<Void>("filmListLink") {
			
			@Override
			public void onClick() {
				setResponsePage(new FilmListPage());
			}
			
		};
		
		userListLink = new Link<Void>("userListLink") {
			
			@Override
			public void onClick() {
				setResponsePage(new UserListPage());
			}
			
			@Override
			public boolean isVisible() {
				return GAJAmdbSession.get().isLoggedIn();
			}
			
		};
		
		WebMarkupContainer addFilmContainer = new WebMarkupContainer("addFilmContainer"){
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		WebMarkupContainer addCSVFilmsContainer = new WebMarkupContainer("addCSVFilmsContainer"){
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		WebMarkupContainer reportedCommentsContainer = new WebMarkupContainer("reportedCommentsContainer"){
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		WebMarkupContainer dividerContainer = new WebMarkupContainer("dividerContainer"){
			@Override
			public boolean isVisible() {
				return user().isAdmin();
			}
		};
		
		profile = new Link<Void>("profile") {

			@Override
			public void onClick() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (session.isLoggedIn())
					setResponsePage(new ProfilePage(session.getLoggedInUser()));
			}
			
		};
		
		addFilm = new Link<Void>("addFilm") {

			@Override
			public void onClick() {
				setResponsePage(new NewFilmPage());
			}
			
		};
		
		addCSVFilms = new Link<Void>("addCSVFilms") {

			@Override
			public void onClick() {
				setResponsePage(new LoadFilmsPage());
			}
			
		};
		
		reportedComments = new Link<Void>("reportedComments") {

			@Override
			public void onClick() {
				setResponsePage(new ReportedCommentsPage());
			}
			
		};
		
		logout = new Link<Void>("logout") {
			
			@Override
			public void onClick() {
				GAJAmdbSession.get().logOut();
				setResponsePage(new HomePage());
			}
		};
		
		// Create navbar user panels
		loginForm = new WebMarkupContainer("loginForm") {
			@Override
			public boolean isVisible() {
				return !GAJAmdbSession.get().isLoggedIn();
			}
		};
		
		userPanel = new WebMarkupContainer("userPanel") {
			@Override
			public boolean isVisible() {
				return GAJAmdbSession.get().isLoggedIn();
			}
		};
		
		
		
		// If there is a user logged in, display his name in the user panel
		usernameLabel = new Label("username", new PropertyModel<String>(userModel, "name"));
		
		add(bannerLink);
		add(loginForm);
		add(userPanel);
		add(userListLink);
		add(filmListLink);
		
		Form<PageHeader> form = new Form<PageHeader>("form", new CompoundPropertyModel<PageHeader>(this)) {
			@Override
			protected void onSubmit() {
				GAJAmdbSession session = GAJAmdbSession.get();

				if (!session.authenticate(email, password, users)) {
					// TODO Localize
					error("Email o contraseña incorrectos");
				}
			}
		};
		
		Link<Void> resetPassword = new Link<Void>("resetPassword") {
			@Override
			public void onClick() {
				setResponsePage(new ResetPasswordPage());
			}
		};
		
		Link<Void> registerUser = new Link<Void>("registerUser") {
			@Override
			public void onClick() {
				setResponsePage(new RegisterPage());
			}
		};
		
		form.add(registerUser);
		form.add(resetPassword);
		form.add(new TextField<String>("email").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("submit", Model.of("")));
		loginForm.add(form);
		
		userPanel.add(addFilmContainer);
		userPanel.add(addCSVFilmsContainer);
		userPanel.add(reportedCommentsContainer);
		userPanel.add(dividerContainer);
		userPanel.add(usernameLabel);
		userPanel.add(profile);
		userPanel.add(logout);
		
		addFilmContainer.add(addFilm);
		addCSVFilmsContainer.add(addCSVFilms);
		reportedCommentsContainer.add(reportedComments);
	}
	
	private User user() {
		GAJAmdbSession session = GAJAmdbSession.get();
		
		if (!session.isLoggedIn()) {
			return null;
		}
		
		return session.getLoggedInUser();
	}
}
