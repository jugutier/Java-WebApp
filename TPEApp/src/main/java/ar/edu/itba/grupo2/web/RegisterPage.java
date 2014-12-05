package ar.edu.itba.grupo2.web;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ar.edu.itba.grupo2.domain.user.User;

public class RegisterPage extends BasePage{
	
	private transient String email;
	private transient String lastname;
	private transient String name;
	private transient String birthdate;
	private transient String password;
	private transient String passwordConfirm;
	private transient String secretQuestion;
	private transient String secretAnswer;
	
	
	public RegisterPage() {
		super();
		Form<RegisterPage> registerUser = new Form<RegisterPage>("registerUser",new CompoundPropertyModel<RegisterPage>(this)){
			@Override
			protected void onSubmit() {
				
				User user = users.getUserByEmail(email);
				if (user != null) {
					// TODO Localize
					error("Mail ya esta en uso");
					
				}
				else {
					DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date birth=null;
					 try {
							birth = outputDateFormat.parse(birthdate);
							} catch (ParseException e) {
								System.out.println("Error parseo");
							}
					 users.registerUser(email, password, passwordConfirm, name, lastname, birth, secretQuestion, secretAnswer);
					 GAJAmdbSession session = GAJAmdbSession.get();
					 session.authenticate(email, password, users);
					setResponsePage(new ProfilePage(session.getLoggedInUser()));
				}
			}
		};
		TextField<String> emailTextField = new TextField<String>("email");
		PasswordTextField passwordTextField = new PasswordTextField("password");
		PasswordTextField passwordConfirmTextField = new PasswordTextField("passwordConfirm");
		TextField<String> nameTextField = new TextField<String>("name");
		TextField<String> lastnameTextField = new TextField<String>("lastname");
		TextField<String> birthdateTextField = new TextField<String>("birthdate");
		TextField<String> sqTextField = new TextField<String>("secretQuestion");
		TextField<String> saTextField = new TextField<String>("secretAnswer");
		birthdateTextField.setRequired(true);
		sqTextField.setRequired(true);
		saTextField.setRequired(true);
		emailTextField.setRequired(true);
		nameTextField.setRequired(true);
		lastnameTextField.setRequired(true);
		passwordTextField.setRequired(true);
		passwordConfirmTextField.setRequired(true);
		registerUser.add(new EqualPasswordInputValidator(passwordTextField, passwordConfirmTextField));
		emailTextField.add(EmailAddressValidator.getInstance());
		registerUser.add(sqTextField);
		registerUser.add(saTextField);
		registerUser.add(birthdateTextField);
		registerUser.add(nameTextField);
		registerUser.add(lastnameTextField);
		registerUser.add(emailTextField);
		registerUser.add(passwordTextField);
		registerUser.add(passwordConfirmTextField);
		
		add(registerUser);
	}

}

