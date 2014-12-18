package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.report.ReportResolution;
import ar.edu.itba.grupo2.domain.report.Resolution;
import ar.edu.itba.grupo2.domain.user.User;

@SuppressWarnings("serial")
public class DiscardReportPage extends BasePage {
	
	private transient String reason;
	
	public DiscardReportPage(final IModel<Comment> comment){
		super();
		Form<DiscardReportPage> reportMessage = new Form<DiscardReportPage>("discardMessage",new CompoundPropertyModel<DiscardReportPage>(this)){
			@Override
			protected void onSubmit() {
				User user = GAJAmdbSession.get().getLoggedInUser();
				ReportResolution rr = new ReportResolution(user, comment.getObject().getUnresolvedReports(), reason, Resolution.DISCARDREPORT);
				comment.getObject().resolve(rr);
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
