package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.memory.MemoryFilmManager;
import ar.edu.itba.grupo2.model.Film;

@SuppressWarnings("serial")
public class WelcomeScreen extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		FilmManagerDAO fm = MemoryFilmManager.getInstance();
		/*for (int i = 0; i < 4; i++) {
			Film film = new Film.Builder()
			.name("JG")
			.director("PURI")
			.releaseDate(new Date())
			.creationDate(new Date())
			.description(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean lacinia dictum velit eget viverra. Phasellus vel fringilla odio. Ut laoreet arcu et magna cursus pharetra. Vestibulum vitae pellentesque lorem. Aenean lacus nisl, sollicitudin placerat venenatis non, tempus eget nisi. Nam justo lacus, malesuada nec posuere sed, malesuada vel lectus. Sed consectetur elit lectus. Etiam sollicitudin sollicitudin ligula, id tristique erat finibus at. Proin tempus magna eu tellus pulvinar tempor. Nulla scelerisque nulla quis odio lobortis vehicula. Nulla blandit dapibus eros, nec aliquet enim molestie non. Donec egestas est id augue iaculis, vitae sodales magna ullamcorper. Nunc vitae nunc sit amet nibh cursus tristique et porta nulla. Etiam sit amet tincidunt sapien. Donec rutrum, libero at laoreet tempus, mi lorem laoreet mauris, ac aliquam orci urna ut nibh. Donec ultrices rhoncus quam. Pellentesque ornare pulvinar dolor, eu placerat turpis luctus at. Cras tristique tellus vel sapien condimentum vulputate nec sit amet ex. Morbi vitae massa ipsum. Pellentesque at accumsan sapien, id vestibulum ex. Mauris elementum gravida lorem consequat ultrices. Maecenas eget mollis tellus. Donec et augue at ex varius sodales. In egestas, libero ut auctor fringilla, magna magna placerat lectus, pulvinar elementum justo magna facilisis lectus. Vivamus dignissim efficitur ipsum non sodales. Duis sit amet eleifend justo. Nam vitae purus arcu. Etiam mi nulla, vestibulum ac sem nec, viverra eleifend dui. Phasellus nec ullamcorper mi. In non lorem vel odio placerat hendrerit. Curabitur ac lectus leo. ")
			.build();
			fm.saveFilm(film);
		}*/
		Calendar c1 = Calendar.getInstance();
		c1.set(2014, 8, 13);
		Film film = null;
		film = new Film.Builder()
		.name("JG")
		.director("PURI")
		.releaseDate(c1.getTime())
		.creationDate(new Date())
		.description(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean lacinia dictum velit eget viverra. Phasellus vel fringilla odio. Ut laoreet arcu et magna cursus pharetra. Vestibulum vitae pellentesque lorem. Aenean lacus nisl, sollicitudin placerat venenatis non, tempus eget nisi. Nam justo lacus, malesuada nec posuere sed, malesuada vel lectus. Sed consectetur elit lectus. Etiam sollicitudin sollicitudin ligula, id tristique erat finibus at. Proin tempus magna eu tellus pulvinar tempor. Nulla scelerisque nulla quis odio lobortis vehicula. Nulla blandit dapibus eros, nec aliquet enim molestie non. Donec egestas est id augue iaculis, vitae sodales magna ullamcorper. Nunc vitae nunc sit amet nibh cursus tristique et porta nulla. Etiam sit amet tincidunt sapien. Donec rutrum, libero at laoreet tempus, mi lorem laoreet mauris, ac aliquam orci urna ut nibh. Donec ultrices rhoncus quam. Pellentesque ornare pulvinar dolor, eu placerat turpis luctus at. Cras tristique tellus vel sapien condimentum vulputate nec sit amet ex. Morbi vitae massa ipsum. Pellentesque at accumsan sapien, id vestibulum ex. Mauris elementum gravida lorem consequat ultrices. Maecenas eget mollis tellus. Donec et augue at ex varius sodales. In egestas, libero ut auctor fringilla, magna magna placerat lectus, pulvinar elementum justo magna facilisis lectus. Vivamus dignissim efficitur ipsum non sodales. Duis sit amet eleifend justo. Nam vitae purus arcu. Etiam mi nulla, vestibulum ac sem nec, viverra eleifend dui. Phasellus nec ullamcorper mi. In non lorem vel odio placerat hendrerit. Curabitur ac lectus leo. ")
		.build();
		fm.saveFilm(film);
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2014, 8, 8);
		
		film = new Film.Builder()
		.name("JG3223")
		.director("PURI")
		.releaseDate(c2.getTime())
		.creationDate(new Date())
		.description(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean lacinia dictum velit eget viverra. Phasellus vel fringilla odio. Ut laoreet arcu et magna cursus pharetra. Vestibulum vitae pellentesque lorem. Aenean lacus nisl, sollicitudin placerat venenatis non, tempus eget nisi. Nam justo lacus, malesuada nec posuere sed, malesuada vel lectus. Sed consectetur elit lectus. Etiam sollicitudin sollicitudin ligula, id tristique erat finibus at. Proin tempus magna eu tellus pulvinar tempor. Nulla scelerisque nulla quis odio lobortis vehicula. Nulla blandit dapibus eros, nec aliquet enim molestie non. Donec egestas est id augue iaculis, vitae sodales magna ullamcorper. Nunc vitae nunc sit amet nibh cursus tristique et porta nulla. Etiam sit amet tincidunt sapien. Donec rutrum, libero at laoreet tempus, mi lorem laoreet mauris, ac aliquam orci urna ut nibh. Donec ultrices rhoncus quam. Pellentesque ornare pulvinar dolor, eu placerat turpis luctus at. Cras tristique tellus vel sapien condimentum vulputate nec sit amet ex. Morbi vitae massa ipsum. Pellentesque at accumsan sapien, id vestibulum ex. Mauris elementum gravida lorem consequat ultrices. Maecenas eget mollis tellus. Donec et augue at ex varius sodales. In egestas, libero ut auctor fringilla, magna magna placerat lectus, pulvinar elementum justo magna facilisis lectus. Vivamus dignissim efficitur ipsum non sodales. Duis sit amet eleifend justo. Nam vitae purus arcu. Etiam mi nulla, vestibulum ac sem nec, viverra eleifend dui. Phasellus nec ullamcorper mi. In non lorem vel odio placerat hendrerit. Curabitur ac lectus leo. ")
		.build();
		fm.saveFilm(film);
		
		//Connection c = ConnectionUtilities.getInstance().getConnection();
		//ConnectionUtilities.getInstance().testQuery(c);
		List<Film> filmList = fm.getAllFilms();
		
		req.setAttribute("topfive", filterTopFilms(filmList, 5));
		req.setAttribute("latest", filterRecentlyAdded(filmList, 5));
		req.setAttribute("newReleases", filterNewReleases(filmList, 7));
		
		req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
	}
	
	private List<Film> filterTopFilms(final List<Film> filmList, final int topAmount) {
		final List<Film> result = new ArrayList<Film>();
		int originalSize = filmList.size();
		int top = (topAmount > originalSize)? originalSize : topAmount;
		
		for (Film f : filmList) {
			result.add(f);
		}
		
		Collections.sort(result, new Comparator<Film>(){

			@Override
			public int compare(Film arg0, Film arg1) {
				return Double.compare(arg0.getScore(), arg1.getScore());
			}
			
		});
		
		return result.subList(0, top);
	}
	
	private List<Film> filterRecentlyAdded(final List<Film> filmList, final int amount) {
		final List<Film> result = new ArrayList<Film>();
		int originalSize = filmList.size();
		int top = (amount > originalSize)? originalSize : amount;
		
		for (Film f : filmList) {
			result.add(f);
		}
		
		Collections.sort(filmList, new Comparator<Film>(){

			@Override
			public int compare(Film arg0, Film arg1) {
				return arg0.getCreationDate().compareTo(arg1.getCreationDate());
			}
			
		});
		
		return result.subList(0, top);
	}
	
	private List<Film> filterNewReleases(final List<Film> filmList, final int dayTolerance) {
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
}
