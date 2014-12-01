package ar.edu.itba.grupo2.web.widget.comment;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.GAJAmdbSession;

@SuppressWarnings("serial")
public class CommentForm extends Panel {
	
	private transient String message;
	private transient int rate;
	
	public CommentForm(String id, IModel<Film> film) {
		super(id, film);
		
		Form<CommentForm> form = new Form<CommentForm>("form", new CompoundPropertyModel<CommentForm>(this)) {
			@Override
			protected void onSubmit() {
				GAJAmdbSession session = GAJAmdbSession.get();
				
				if (!session.isLoggedIn()) {
					// TODO Localize
					error("No estas loggeado");
					return;
				}
				
				User user = session.getLoggedInUser();
				
				Comment comment = new Comment.Builder()
					.text(message)
					.rate(rate)
					.user(user)
					.film(film())
					.build();
				
				try {
					film().addComment(comment);
					message = null;
					rate = 0;
				} catch (UserCantCommentException e) {
					// TODO Localize
					error("No puedes comentar");
				}
			}
		};
		
		DropDownChoice<Integer> rateDropDown = new DropDownChoice<Integer>("rate", new LoadableDetachableModel<List<Integer>>() {
			
			@Override
			protected List<Integer> load() {
				List<Integer> list = new ArrayList<Integer>();
				
				for(int i = 0; i < 6; i++) {
					list.add(i);
				}
				
				return list;
			}
		});
		
		TextArea<String> messageTextArea = new TextArea<String>("message");
		
		messageTextArea.setRequired(true);
		// TODO Check this value
		messageTextArea.add(StringValidator.maximumLength(300));
		
		form.add(rateDropDown);
		form.add(messageTextArea);
		add(form);
	}
	
	@Override
	public boolean isVisible() {
		GAJAmdbSession session = GAJAmdbSession.get();
		return session.isLoggedIn() && film().userCanComment(session.getLoggedInUser());
	}
	
	private Film film() {
		return ((Film) getDefaultModelObject());
	}

}
