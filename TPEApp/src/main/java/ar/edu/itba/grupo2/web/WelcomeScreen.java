package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.FilmManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.service.FilmService;

@SuppressWarnings("serial")
public class WelcomeScreen extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FilmManagerDAO fm = new FilmManagerPSQLImpl();
		FilmService filmService = FilmService.getInstance();
		List<Film> filmList = fm.getAllFilms();

		req.setAttribute("topfive", filmService.filterTopFilms(filmList, 5));
		req.setAttribute("latest", filmService.filterRecentlyAdded(filmList, 5));
		req.setAttribute("newReleases",
				filmService.filterNewReleases(filmList, 7));

		req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
	}
}
