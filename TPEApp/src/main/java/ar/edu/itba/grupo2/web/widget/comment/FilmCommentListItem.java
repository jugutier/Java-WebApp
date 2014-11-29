package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.commentRate.CommentRate;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;

@SuppressWarnings("serial")
public class FilmCommentListItem extends Panel {

	@SpringBean
	private UserRepo users;
	
	public FilmCommentListItem(String id, IModel<Comment> comment) {
		super(id, comment);
		
		CompoundPropertyModel<Comment> compoundModel;
		setDefaultModel(compoundModel = new CompoundPropertyModel<Comment>(comment));
		
		// TODO Localize this
		Label adminTag = new Label("adminTag", "Admin") {
			@Override
			public boolean isVisible() {
				return comment().getUser().isAdmin();
			}
		};
		
		Label vipTag = new Label("vipTag", "V.I.P.") {
			@Override
			public boolean isVisible() {
				return comment().getUser().isVip();
			}
		};
		
		Label commentRating = new Label("rate");
		
		Link<Void> usernameLink = new Link<Void>("usernameLink") {

			@Override
			public void onClick() {
				// TODO Go to user's profile
			}
			
		};
		
		Link<Void> reportButton = new Link<Void>("reportButton") {

			@Override
			public void onClick() {
				// TODO Report this comment
			}
			
			@Override
			public boolean isVisible() {
				return GAJAmdbSession.get().isLoggedIn();
			}
			
		};
		
		Link<Comment> deleteButton = new Link<Comment>("deleteButton", comment) {

			@Override
			public void onClick() {
				comment().getFilm().removeComment(comment());
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
			
		};
		
		Form<CommentRate> ratingForm = new Form<CommentRate>("commentRateForm"){
			
			@Override
			public boolean isVisible() {
				return GAJAmdbSession.get().isLoggedIn();
			}
			
		};
		
		add(usernameLink);
		usernameLink.add(new Label("username", compoundModel.bind("user.name")));
		// TODO Revise this
		add(new StarScoreIndicator("scoreStars", 3));
		add(new Label("text"));
		add(adminTag);
		add(vipTag);
		add(reportButton);
		add(deleteButton);
		add(ratingForm);
		add(commentRating);
	}
	
	private Comment comment() {
		return (Comment) getDefaultModelObject();
	}

}
