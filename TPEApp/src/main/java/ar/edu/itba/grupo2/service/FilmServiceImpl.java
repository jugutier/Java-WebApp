package ar.edu.itba.grupo2.service;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class FilmServiceImpl implements FilmService {

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
}
