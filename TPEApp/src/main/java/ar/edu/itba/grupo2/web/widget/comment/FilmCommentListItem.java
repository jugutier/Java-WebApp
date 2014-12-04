package ar.edu.itba.grupo2.web.widget.comment;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.ProfilePage;
import ar.edu.itba.grupo2.web.widget.StarScoreIndicator;
import ar.edu.itba.grupo2.web.widget.user.UserRoleBadges;

@SuppressWarnings("serial")
public class FilmCommentListItem extends Panel {

	@SpringBean
	private UserRepo users;
	
	private int rating;
	
	public FilmCommentListItem(String id, IModel<Comment> comment, final IModel<List<Comment>> listModel) {
		super(id, comment);
		
		final CompoundPropertyModel<Comment> compoundModel;
		setDefaultModel(compoundModel = new CompoundPropertyModel<Comment>(comment));
		
		Label commentRating = new Label("rate");
		
		Link<Void> usernameLink = new Link<Void>("usernameLink") {

			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(comment().getUser()));
			}
			
		};
		
		Link<Comment> reportButton = new Link<Comment>("reportButton", comment) {

			@Override
			public void onClick() {
				comment().report(GAJAmdbSession.get().getLoggedInUser());
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && comment().isReportable() && !comment().isReportedByUser(session.getLoggedInUser());
			}
			
		};
		
		Link<Comment> deleteButton = new Link<Comment>("deleteButton", comment) {

			@Override
			public void onClick() {
				comment().remove();
				listModel.detach();
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
			
		};
		
		Form<FilmCommentListItem> ratingForm = new Form<FilmCommentListItem>("commentRateForm", new CompoundPropertyModel<FilmCommentListItem>(this)) {
			
			@Override
			public void onSubmit() {
				comment().rate(GAJAmdbSession.get().getLoggedInUser(), rating);
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !comment().isBelongsToUser() && !comment().isRatedBy(session.getLoggedInUser());
			}
			
		};
		
		DropDownChoice<Integer> rateDropDown = new DropDownChoice<Integer>("rating", new LoadableDetachableModel<List<Integer>>() {
			
			@Override
			protected List<Integer> load() {
				List<Integer> list = new ArrayList<Integer>();
				
				for(int i = 0; i < 6; i++) {
					list.add(i);
				}
				
				return list;
			}
		});
		
		ratingForm.add(rateDropDown);
		
		add(usernameLink);
		usernameLink.add(new Label("username", compoundModel.bind("user.name")));
		// TODO Revise this
		add(new StarScoreIndicator("scoreStars", new PropertyModel<Integer>(comment, "filmRate")));
		add(new Label("text"));
		add(new UserRoleBadges("roleBadges", new PropertyModel<User>(comment, "user")));
		add(reportButton);
		add(deleteButton);
		add(ratingForm);
		add(commentRating);
	}
	
	private Comment comment() {
		return (Comment) getDefaultModelObject();
	}

}
