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
		else if (film.getName().length() > 100) {
			errors.rejectValue("name", "tooLong");
		}
		
		if (ValidationUtilities.paramEmpty(film.getDirector())) {
			errors.rejectValue("director", "empty");
		}
		else if(film.getDirector().length() > 40) {
			errors.rejectValue("director", "tooLong");
		}
		
		if (ValidationUtilities.paramEmpty(film.getReleaseDate())) {
			errors.rejectValue("releaseDate", "empty");
		}
		
		if (ValidationUtilities.paramEmpty(film.getDescription())) {
			errors.rejectValue("description", "empty");
		}
		else if (film.getDescription().length() > 500) {
			errors.rejectValue("description", "tooLong");
		}
		
		if (film.getLength() <= 0) {
			errors.rejectValue("length", "invalid.length");
		}
		
		if (!film.getMovieImage().getContentType().equals("image/jpg") || film.getMovieImage().getContentType().equals("image/png")) {
			errors.rejectValue("movieImage", "invalid.movieImageType");
		}
	}
}
