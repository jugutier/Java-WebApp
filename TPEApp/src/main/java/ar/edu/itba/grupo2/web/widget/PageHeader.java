package ar.edu.itba.grupo2.web.widget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.grupo2.web.FilmListPage;
import ar.edu.itba.grupo2.web.HomePage;

@SuppressWarnings("serial")
public class PageHeader extends Panel {

	public PageHeader(String id) {
		super(id);
		
		add(new Link<Void>("banner") {

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
			
		});
		
		add(new Link<Void>("film-list-link") {
			
			@Override
			public void onClick() {
				setResponsePage(new FilmListPage());
			}
			
		});
		
		Link<Void> userListLink = new Link<Void>("user-list-link") {
			
			@Override
			public void onClick() {
				setResponsePage(new FilmListPage());
			}
			
		};
		
		WebMarkupContainer loginForm = new WebMarkupContainer("login-form");
		WebMarkupContainer userPanel = new WebMarkupContainer("user-panel");
		
		userPanel.setVisible(false);
		userListLink.setVisible(false);
		
		add(loginForm);
		add(userPanel);
		add(userListLink);
	}

}
