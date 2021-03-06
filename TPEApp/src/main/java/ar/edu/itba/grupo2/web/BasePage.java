package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.CommentRepo;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.widget.PageHeader;

@SuppressWarnings("serial")
public class BasePage extends WebPage {
	
	@SpringBean
	protected FilmRepo films;
	
	@SpringBean
	protected UserRepo users;
	
	@SpringBean
	protected CommentRepo comments;
	
	public BasePage() {
		super();
		
		FeedbackPanel errorsFeedback = new FeedbackPanel("errorsFeedback") {
			@Override
			public boolean isVisible() {
				return this.anyErrorMessage();
			};
		};
		
		add(errorsFeedback);
		add(new PageHeader("pageHeader"));
	}
	
	protected GAJAmdbSession getUserSession() {
		return (GAJAmdbSession) getSession();
	}

}
