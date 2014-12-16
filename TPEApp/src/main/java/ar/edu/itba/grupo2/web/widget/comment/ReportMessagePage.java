package ar.edu.itba.grupo2.web.widget.comment;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.web.BasePage;
import ar.edu.itba.grupo2.web.GAJAmdbSession;
import ar.edu.itba.grupo2.web.HomePage;

@SuppressWarnings("serial")
public class ReportMessagePage extends BasePage {
	
	private transient String reason;
	
	public ReportMessagePage(final IModel<Comment> actualComment){
		super();
		Form<ReportMessagePage> reportMessage = new Form<ReportMessagePage>("reportMessage",new CompoundPropertyModel<ReportMessagePage>(this)){
			@Override
			protected void onSubmit() {
				actualComment.getObject().report(GAJAmdbSession.get().getLoggedInUser(), reason);
				actualComment.detach();
				setResponsePage(new HomePage());
				
			}
		};
		TextField<String> reasonTextField = new TextField<String>("reason");
		reasonTextField.setRequired(true);
		reasonTextField.add(StringValidator.maximumLength(140));
		reportMessage.add(reasonTextField);
		add(reportMessage);
	}


}
