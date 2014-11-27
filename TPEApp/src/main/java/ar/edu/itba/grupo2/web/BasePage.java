package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.widget.PageHeader;

public class BasePage extends WebPage {
	
	@SpringBean
	protected FilmRepo films;
	
	@SpringBean
	protected UserRepo users;
	
	public BasePage() {
		super();
		add(new PageHeader("page-header"));
	}
	
	protected GAJAmdbSession getUserSession() {
		return (GAJAmdbSession) getSession();
	}

}
