package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.service.FilmService;

@SuppressWarnings("serial")
public class FilmList extends BaseWebServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		super.doGet(req, resp);
		FilmService filmService = FilmService.getInstance();
		
		String genreFilter = req.getParameter("genre");
		String directorFilter = req.getParameter("director");
		
		List<Film> filmList = filmService.getAllFilms();
		List<String> genreList = filmService.getGenres();
		
		if (genreFilter != null) {
			filmList = filmService.filterByGenre(filmList, genreFilter);
		}
		
		if (directorFilter != null) {
			filmList = filmService.filterByDirector(filmList, directorFilter);
		}
		
		req.setAttribute("filmList", filmList);
		req.setAttribute("genreList", genreList);
		req.getRequestDispatcher("/WEB-INF/jsp/filmList.jsp").forward(req, resp);
	}
}
