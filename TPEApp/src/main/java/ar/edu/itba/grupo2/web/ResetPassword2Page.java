package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.user.User;

@SuppressWarnings("serial")
public class ResetPassword2Page extends BasePage {
	
	private transient String answer;
	private transient String password;
	@SuppressWarnings("unused")
	private transient String confirmPassword;

	public ResetPassword2Page(User user) {
		super();
		
		setDefaultModel(new EntityModel<User>(User.class, user));
		
		Form<ResetPassword2Page> form = new Form<ResetPassword2Page>("form", new CompoundPropertyModel<ResetPassword2Page>(this)) {
			@Override
			protected void onSubmit() {
				if (user().resetPassword(answer, password) == true) {
					setResponsePage(new HomePage());
				}
				else {
					error(new StringResourceModel("error.incorrectSecretAnswer", this, null).getString());
				}
			}
		};
		
		TextField<String> answerField = new TextField<String>("answer");
		answerField.setRequired(true);
		
		PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setRequired(true);
		
		PasswordTextField confirmPasswordField = new PasswordTextField("confirmPassword");
		confirmPasswordField.setRequired(true);
		
		form.add(new EqualPasswordInputValidator(passwordField, confirmPasswordField));
		form.add(answerField);
		form.add(passwordField);
		form.add(confirmPasswordField);
		add(form);
		
		form.add(new Label("question", new PropertyModel<String>(getDefaultModel(), "secretQuestion")));
	}
	
	private User user() {
		return (User) getDefaultModelObject();
	}
}
