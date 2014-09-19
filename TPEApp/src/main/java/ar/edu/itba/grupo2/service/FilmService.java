package ar.edu.itba.grupo2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.FilmManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.Genre;
import ar.edu.itba.grupo2.model.User;

public class FilmService {

	private static FilmService instance = null;

	private final FilmManagerDAO filmManager = FilmManagerPSQLImpl
			.getInstance();

	private FilmService() {

	}

	public static FilmService getInstance() {
		if (instance == null) {
			instance = new FilmService();
		}

		return instance;
	}

	public List<Film> getAllFilms() {
		return filmManager.getAllFilms();
	}

	public Film getFilmById(final int id) throws FilmNotFoundException {
		Film ret = filmManager.getFilmById(id);
		if (ret == null) {
			throw new FilmNotFoundException();
		}
		return ret;
	}

	public List<Comment> getCommentsForFilm(final Film film)
			throws FilmNotFoundException {
		if (film == null) {
			throw new IllegalArgumentException();
		}
		getFilmById(film.getId());// validate for non existent film
		List<Comment> ret = filmManager.getCommentsForFilm(film);
		return ret;
	}

	public List<String> getGenres() {
		Genre[] genres = Genre.values();
		List<String> ret = new LinkedList<String>();
		for (Genre genre : genres) {
			ret.add(genre.toString());
		}
		return ret;
	}

	public boolean userHasCommentedFilm(final Film film, final User user)
			throws FilmNotFoundException {
		if (film == null || user == null) {
			throw new IllegalArgumentException();
		}
		List<Comment> comments = filmManager.getCommentsForFilm(film);
		if (comments == null) {
			throw new FilmNotFoundException();
		}
		for (Comment c : comments) {
			if (c.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	public List<Film> filterTopFilms(final List<Film> filmList,
			final int topAmount) {
		if (filmList == null || topAmount <= 0) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>(topAmount);
		int originalSize = filmList.size();
		int top = (topAmount > originalSize) ? originalSize : topAmount;

		for (Film f : filmList) {
			result.add(f);
		}

		Collections.sort(result, new Comparator<Film>() {

			@Override
			public int compare(Film arg0, Film arg1) {
				return Double.compare(arg1.getScore(), arg0.getScore());
			}

		});

		return result.subList(0, top);
	}

	public List<Film> filterRecentlyAdded(final List<Film> filmList,
			final int amount) {
		if (filmList == null || amount <= 0) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>(amount);
		int originalSize = filmList.size();
		int top = (amount > originalSize) ? originalSize : amount;

		for (Film f : filmList) {
			result.add(f);
		}

		Collections.sort(result, new Comparator<Film>() {

			@Override
			public int compare(Film arg0, Film arg1) {
				return -arg0.getCreationDate()
						.compareTo(arg1.getCreationDate());
			}

		});

		return result.subList(0, top);
	}

	public List<Film> filterNewReleases(final List<Film> filmList,
			final int dayTolerance) {
		if (filmList == null || dayTolerance < 0) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>();
		final Date today = new Date();
		long startTime = 0;
		long endTime = 0;
		long diffTime = 0;
		long diffDays = 0;

		for (Film f : filmList) {
			startTime = f.getReleaseDate().getTime();
			endTime = today.getTime();
			diffTime = endTime - startTime;
			diffDays = diffTime / (1000 * 60 * 60 * 24);

			if (diffDays < dayTolerance && diffDays >= 0) {
				result.add(f);
			}
		}

		return result;
	}

	public List<Film> filterByGenre(final List<Film> filmList,
			final String genre) {
		if (filmList == null || genre == null) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>();

		boolean dontFilter = (genre == null || genre.isEmpty());
		if (dontFilter) {
			return filmList;
		} else {
			Genre filter = Genre.fromString(genre);
			if (filter != null) {
				for (Film f : filmList) {
					if (f.getGenre() != null && f.getGenre().equals(filter)) {
						result.add(f);
					}
				}
			}
		}

		return result;
	}

	public List<Film> filterByDirector(final List<Film> filmList,
			final String director) {
		if (filmList == null || director == null) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>();
		boolean dontFilter = (director == null || director.isEmpty());

		for (Film f : filmList) {
			// Add Films where the given string is contained within said Film's
			// director. Case insensitive.
			if (dontFilter
					|| f.getDirector().toUpperCase()
							.contains(director.toUpperCase())) {
				result.add(f);
			}
		}

		return result;
	}

	public List<Film> orderByReleaseDate(final List<Film> filmList) {
		if (filmList == null) {
			throw new IllegalArgumentException();
		}
		final List<Film> result = new ArrayList<Film>();

		for (Film f : filmList) {
			result.add(f);
		}

		Collections.sort(result, new Comparator<Film>() {

			@Override
			public int compare(Film arg0, Film arg1) {
				return -arg0.getReleaseDate().compareTo(arg1.getReleaseDate());
			}

		});

		return result;
	}

	public Comment saveComment(final Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException();
		}
		filmManager.saveFilm(comment.getFilm());
		return filmManager.saveComment(comment);
	}

	public boolean userCanComment(final Film film, final User user)
			throws FilmNotFoundException {
		if (film == null || user == null) {
			throw new IllegalArgumentException();
		}
		if (userHasCommentedFilm(film, user)) {
			return false;
		} else if (film.isReleased() || user.isVip()) {
			return true;
		}
		return false;
	}

	public Comment addComment(final Comment comment) {
		if (comment == null || comment.getText() == "") {
			throw new IllegalArgumentException();
		}
		return filmManager.saveComment(comment);
	}
}
