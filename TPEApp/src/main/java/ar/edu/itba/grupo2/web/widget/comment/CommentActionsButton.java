package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.report.ReportResolution;
import ar.edu.itba.grupo2.domain.report.Resolution;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.ProfilePage;

@SuppressWarnings("serial")
public class CommentActionsButton extends Panel {

	public CommentActionsButton(String id, final IModel<Comment> comment) {
		super(id, comment);
		
		Link<Void> profileButton = new Link<Void>("profileButton") {

			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(comment().getUser()));
			}
			
		};
		
		Link<Comment> followButton = new Link<Comment>("followButton", comment) {
			
			@Override
			public void onClick() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (session.isLoggedIn())
					session.getLoggedInUser().followUser(comment().getUser());
			};
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !comment().getUser().equals(session.getLoggedInUser()) && comment().getUser().isFollowable(session.getLoggedInUser());
			}
		};
		
		Link<Comment> unfollowButton = new Link<Comment>("unfollowButton", comment) {
			
			@Override
			public void onClick() {
				GAJAmdbSession session = GAJAmdbSession.get();
				if (session.isLoggedIn())
					session.getLoggedInUser().unFollowUser(comment().getUser());
			};
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !comment().getUser().equals(session.getLoggedInUser()) && !comment().getUser().isFollowable(session.getLoggedInUser());
			}
		};
		
		Link<Comment> muteButton = new Link<Comment>("muteButton", comment) {

			@Override
			public void onClick() {
				comment().getUser().setMuted(true);
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin() && !session.getLoggedInUser().equals(comment().getUser()) && !comment().getUser().isMuted();
			}
			
		};
		
		Link<Comment> unmuteButton = new Link<Comment>("unmuteButton", comment) {

			@Override
			public void onClick() {
				comment().getUser().setMuted(false);
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin() && !session.getLoggedInUser().equals(comment().getUser()) && comment().getUser().isMuted();
			}
			
		};
		
		Link<Comment> reportButton = new Link<Comment>("reportButton", comment) {

			@Override
			public void onClick() {
				setResponsePage(new ReportMessagePage(comment));
				
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && !session.getLoggedInUser().equals(comment().getUser()) && !comment().isReportedByUser(session.getLoggedInUser());
			}
			
		};
		
		WebMarkupContainer adminSeparator = new WebMarkupContainer("adminSeparator") {
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			};
		};
		
		Link<Comment> discardReports = new Link<Comment>("discardReports", comment) {

			@Override
			public void onClick() {
				User user = GAJAmdbSession.get().getLoggedInUser();
				ReportResolution rr = new ReportResolution(user, getModelObject().getUnresolvedReports(), "Porque yo lo digo (Disc)", Resolution.DISCARDREPORT);
				getModelObject().resolve(rr);
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && comment().isReported();
			}
			
		};
		
		Link<Comment> deleteButton = new Link<Comment>("deleteButton", comment) {

			@Override
			public void onClick() {
				//comment().remove();
				User user = GAJAmdbSession.get().getLoggedInUser();
				ReportResolution rr = new ReportResolution(user, getModelObject().getUnresolvedReports(), "Porque yo lo digo (Dele)", Resolution.DELETE);
				getModelObject().resolve(rr);
			}
			
			@Override
			public boolean isVisible() {
				GAJAmdbSession session = GAJAmdbSession.get();
				return session.isLoggedIn() && session.getLoggedInUser().isAdmin();
			}
			
		};

		add(profileButton);
		add(followButton);
		add(unfollowButton);
		add(muteButton);
		add(unmuteButton);
		add(reportButton);
		add(adminSeparator);
		add(discardReports);
		add(deleteButton);
	}
	
	private Comment comment() {
		return (Comment) getDefaultModelObject();
	}
}
