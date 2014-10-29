package ar.edu.itba.grupo2.web.command.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.grupo2.domain.comment.Comment;

public class FilmFormValidator implements Validator{
	@Override
	public boolean supports(Class<?> arg0) {
		return Comment.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "required", "Field is required.");
	}
}
