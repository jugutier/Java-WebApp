package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

public class FilmCommentListItem extends Panel {

	@SpringBean
	private UserRepo users;
	
	public FilmCommentListItem(String id, Comment comment) {
		super(id);
		
		Label adminTag = new Label("admin-tag", "Admin");
		Label vipTag = new Label("vip-tag", "V.I.P.");
		Link<Void> usernameLink = new Link<Void>("username-link") {

			@Override
			public void onClick() {
				// TODO Go to user's profile
			}
			
		};
		
		Link<Void> reportButton = new Link<Void>("report-button") {

			@Override
			public void onClick() {
				// TODO Report this comment
			}
			
		};
		
		Link<Void> deleteButton = new Link<Void>("delete-button") {

			@Override
			public void onClick() {
				// TODO Delete this comment
				
			}
			
		};
		
		Form ratingForm = new Form<>("comment-rate-form");
		
		User user = comment.getUser();
		User loggedInUser = GAJAmdbSession.get().getLoggedInUser(users);
		
		vipTag.setVisible(user.isVip());
		adminTag.setVisible(user.isAdmin());
		reportButton.setVisible(loggedInUser != null);
		deleteButton.setVisible(loggedInUser != null && loggedInUser.isAdmin());
		ratingForm.setVisible(loggedInUser != null);
		
		add(usernameLink);
		usernameLink.add(new Label("username", comment.getUser().getName()));
		add(new StarScoreIndicator("score-stars", comment.getFilmRate(), 5));
		add(new Label("message", comment.getText()));
		add(adminTag);
		add(vipTag);
		add(reportButton);
		add(deleteButton);
		add(ratingForm);
	}

}
