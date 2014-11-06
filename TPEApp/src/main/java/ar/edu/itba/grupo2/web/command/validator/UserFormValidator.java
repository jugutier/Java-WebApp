package ar.edu.itba.grupo2.web.command.validator;

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
		}
		else if (user.getEmail().length() > 100) {
			errors.rejectValue("email", "tooLong");
		}
		else if (!ValidationUtilities.isEmail(user.getEmail())) {
			errors.rejectValue("email","invalidMail");
		}
		
		if (ValidationUtilities.paramEmpty(user.getName())) {
			errors.rejectValue("name","empty");
		}
		else if (user.getName().length() > 15) {
			errors.rejectValue("name", "tooLong");
		}
		
		if (ValidationUtilities.paramEmpty(user.getLastname())) {
			errors.rejectValue("lastname","empty");
		}
		else if (user.getLastname().length() > 15) {
			errors.rejectValue("lastname", "tooLong");
		}
		
		if (ValidationUtilities.paramEmpty(user.getPassword())) {
			errors.rejectValue("password","empty");
		}
		else if (user.getPassword().length() > 10) {
			errors.rejectValue("password", "tooLong");
		}
		else if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm","noCoincidence");
		}
		
		if (ValidationUtilities.paramEmpty(user.getSecretQuestion())) {
			errors.rejectValue("secretQuestion","empty");
		}
		else if (user.getSecretQuestion().length() > 140) {
			errors.rejectValue("secretQuestion", "tooLong");
		}
		
		if (ValidationUtilities.paramEmpty(user.getSecretAnswer())) {
			errors.rejectValue("secretAnswer","empty");
		}
		else if (user.getSecretAnswer().length() > 140) {
			errors.rejectValue("secretAnswer", "tooLong");
		}
		
		if(ValidationUtilities.paramEmpty(user.getBirthdate())){
			errors.rejectValue("birthdate", "empty");
		}
		
		
	}
}
