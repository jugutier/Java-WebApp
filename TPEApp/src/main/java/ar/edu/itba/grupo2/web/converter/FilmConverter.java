package ar.edu.itba.grupo2.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;

@Component
public class FilmConverter implements Converter<Integer, Film> {

	private final FilmRepo filmRepo;
	
	@Autowired
	public FilmConverter(FilmRepo filmRepo) {
		this.filmRepo = filmRepo;
	}

	@Override
	public Film convert(Integer id) {
		return filmRepo.get(id);
	}
}
