package ar.edu.itba.grupo2.web.command.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.command.UserForm;

@Component
public class UserFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return UserForm.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm user = (UserForm) target;
		if (ValidationUtilities.paramEmpty(user.getEmail())) {
			errors.rejectValue("email", "empty");
		} else if (!ValidationUtilities.isEmail(user.getEmail())) {
			errors.rejectValue("email","invalidMail");
		}
		if (ValidationUtilities.paramEmpty(user.getName())) {
			errors.rejectValue("name","empty");
		}
		if (ValidationUtilities.paramEmpty(user.getLastname())) {
			errors.rejectValue("lastname","empty");
		}
		if (ValidationUtilities.paramEmpty(user.getPassword())) {
			errors.rejectValue("password","empty");
		} else if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm","noCoincidence");
		}
		if (ValidationUtilities.paramEmpty(user.getSecretQuestion())) {
			errors.rejectValue("secretQuestion","empty");
		}
		if (ValidationUtilities.paramEmpty(user.getSecretAnswer())) {
			errors.rejectValue("secretAnswer","empty");
		}
		if(ValidationUtilities.paramEmpty(user.getBirthdate())){
			errors.rejectValue("birthdate", "empty");
		}
		
		
	}
}
