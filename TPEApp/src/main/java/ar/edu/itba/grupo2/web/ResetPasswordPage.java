package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ar.edu.itba.grupo2.domain.user.User;

@SuppressWarnings("serial")
public class ResetPasswordPage extends BasePage {
	
	private transient String email;

	public ResetPasswordPage() {
		super();
		
		Form<ResetPasswordPage> emailStage = new Form<ResetPasswordPage>("emailStage", new CompoundPropertyModel<ResetPasswordPage>(this)) {
			@Override
			protected void onSubmit() {
				User user = users.getUserByEmail(email);
				if (user != null) {
					setResponsePage(new ResetPassword2Page(user));
				}
				else {
					// TODO Localize
					error("No existe");
				}
			}
		};
		
		TextField<String> email = new TextField<String>("email");
		email.setRequired(true);
		email.add(EmailAddressValidator.getInstance());
		
		add(emailStage);
		emailStage.add(email);
	}
}
