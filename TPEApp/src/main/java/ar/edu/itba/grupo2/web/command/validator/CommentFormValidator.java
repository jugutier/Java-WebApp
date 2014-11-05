package ar.edu.itba.grupo2.web.command.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.web.command.CommentForm;

@Component
public class CommentFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Comment.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "required");
		CommentForm cf = (CommentForm)target;
		// The length could go to a config file...
		if(cf.getText().length() > 140){
			errors.rejectValue("text","tooLong");
		}
	}

}
