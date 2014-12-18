package ar.edu.itba.grupo2.service;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.stereotype.Service;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;

@Service
public class FilmServiceImpl implements FilmService {

	@SpringBean
	private FilmRepo films;

	@Override
	public int getStock(String filmName) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		query.whereEqualTo("name", filmName);
		try {
			List<ParseObject> result = query.find();
			return result == null ? 0 : query.find().get(0).getInt("stock");
		} catch (ParseException e) {
			return 0;
		}
	}

	@Override
	public void getFromFile(File csvFile) throws Exception {
		CSVParser parser = CSVParser.parse(csvFile, Charset.forName("UTF-8"),
				CSVFormat.DEFAULT.withHeader("title", "releaseDate",
						"director", "length", "genre", "description"));
		List<CSVRecord> records = parser.getRecords();

		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = null;
		Film film;

		// Remove header
		records.remove(0);
		for (CSVRecord r : records) {
			releaseDate = outputDateFormat.parse(r.get("releaseDate"));
			List<Genre> genres = new ArrayList<Genre>();
			String[] genresValues = r.get("genre").replace("\"", "").split(",");
			for (String genre : genresValues) {
				genres.add(films.getGenre(genre));
			}
			film = new Film.Builder().creationDate(new Date())
					.releaseDate(releaseDate).name(r.get("title"))
					.director(r.get("director")).genres(genres)
					.length(Integer.parseInt(r.get("length").trim()))
					.description(r.get("description")).build();
			films.save(film);

		}
	}
}
