package ar.edu.itba.grupo2.web;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ar.edu.itba.grupo2.domain.user.User;

@SuppressWarnings("serial")
public class RegisterPage extends BasePage{
	
	private transient String email;
	private transient String lastname;
	private transient String name;
	private transient String birthdate;
	private transient String password;
	private transient String passwordConfirm;
	private transient String secretQuestion;
	private transient String secretAnswer;
	private transient CaptchaImageResource captchaImageResource;
	private transient String imagePass = randomString(6, 8);
	private transient ValueMap properties = new ValueMap();
	
	
	public RegisterPage() {
		super();
		Form<RegisterPage> registerUser = new Form<RegisterPage>("registerUser",new CompoundPropertyModel<RegisterPage>(this)){
			@Override
			protected void onSubmit() {
				if (imagePass.equals(getPassword())){
				User user = users.getUserByEmail(email);
					if (user != null) {
						error(new StringResourceModel("error.mailUsed", this, null).getString());
					}
					else {
						DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date birth=null;
						try {
							birth = outputDateFormat.parse(birthdate);
						} catch (ParseException e) {
							error(new StringResourceModel("error.dateFormat", this, null).getString());
							return ;
						}
						users.registerUser(email, password, passwordConfirm, name, lastname, birth, secretQuestion, secretAnswer);
						GAJAmdbSession session = GAJAmdbSession.get();
						session.authenticate(email, password, users);
						setResponsePage(new ProfilePage(session.getLoggedInUser()));
					}
				}else{
					Object[] values = {getPassword(),imagePass};
					error(new StringResourceModel("error.captcha", this, null, values).getString());
					
					captchaImageResource.invalidate();
				}
			}

		};
		
		captchaImageResource = new CaptchaImageResource(imagePass);
        registerUser.add(new Image("captchaImage", captchaImageResource));
        registerUser.add(new RequiredTextField<String>("passkey", new PropertyModel<String>(properties,
             "passkey")){
	             @Override
	             protected final void onComponentTag(final ComponentTag tag)
	             {
	                 super.onComponentTag(tag);
	                 // clear the field after each render
	                 tag.put("value", "");
	             }
         	});
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
	
	private static int randomInt(int min, int max)
    {
        return (int)(Math.random() * (max - min) + min);
    }

    private static String randomString(int min, int max)
    {
        int num = randomInt(min, max);
        byte b[] = new byte[num];
        for (int i = 0; i < num; i++)
            b[i] = (byte)randomInt('a', 'z');
        return new String(b);
    }
    
    private String getPassword()
    {
        return properties.getString("passkey");
    }

}

