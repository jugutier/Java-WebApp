package ar.edu.itba.grupo2.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;

@Component
public class GenreConverter implements Converter<String, Genre> {
	private FilmRepo filmRepo;
	
	@Autowired
	public GenreConverter(FilmRepo filmRepo) {
		this.filmRepo = filmRepo;
	}
	
	public Genre convert(String genre) {
		return filmRepo.getGenre(genre);//new Genre(genre); 
	}
}