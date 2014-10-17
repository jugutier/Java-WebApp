package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.service.FilmService;
import ar.edu.itba.grupo2.service.impl.FilmServiceImpl;

@SuppressWarnings("serial")
public class WelcomeScreen extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		FilmService filmService = FilmServiceImpl.getInstance();

		List<Film> filmList = filmService.getAllFilms();	
		
		List<Film> topfive = filmService.filterTopFilms(filmList, 5);
		req.setAttribute("topfive",topfive);
		req.setAttribute("latest", filmService.filterRecentlyAdded(filmList, 5));
		req.setAttribute("newReleases",
				filmService.filterNewReleases(filmList, 7));

		req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
	}
}
