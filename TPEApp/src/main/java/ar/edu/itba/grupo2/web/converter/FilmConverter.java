package ar.edu.itba.grupo2.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;

@Component
public class FilmConverter implements Converter<String, Film> {

	private final FilmRepo filmRepo;
	
	@Autowired
	public FilmConverter(FilmRepo filmRepo) {
		this.filmRepo = filmRepo;
	}

	@Override
	public Film convert(String id) {
		return filmRepo.get(Integer.valueOf(id));
	}
}
