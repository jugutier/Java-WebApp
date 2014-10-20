package ar.edu.itba.grupo2.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.genre.Genre;

@Component
public class GenreConverter implements Converter<String, Genre> {

	
	public Genre convert(String genre) {
		return new Genre(genre); 
	}
}