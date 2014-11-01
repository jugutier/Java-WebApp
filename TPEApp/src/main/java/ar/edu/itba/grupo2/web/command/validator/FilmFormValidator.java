package ar.edu.itba.grupo2.web.command.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.command.FilmForm;

@Component
public class FilmFormValidator implements Validator{
	@Override
	public boolean supports(Class<?> arg0) {
		return Comment.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FilmForm film = (FilmForm) target;
		
		if (ValidationUtilities.paramEmpty(film.getName())) {
			errors.rejectValue("name", "empty");
		}
		
		if (ValidationUtilities.paramEmpty(film.getDirector())) {
			errors.rejectValue("director", "empty");
		}
		
		if (ValidationUtilities.paramEmpty(film.getReleaseDate())) {
			errors.rejectValue("releaseDate", "empty");
		}
		
		if (ValidationUtilities.paramEmpty(film.getDescription())) {
			errors.rejectValue("description", "empty");
		}
		
		if (film.getLength() <= 0) {
			errors.rejectValue("length", "invalid");
		}
	}
}
