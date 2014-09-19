package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.service.FilmService;
import ar.edu.itba.grupo2.service.UserManager;

@SuppressWarnings("serial")
public class FilmList extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {

		FilmService filmService = FilmService.getInstance();
		req.setCharacterEncoding("UTF-8");
		String genreFilter = req.getParameter("genre");
		String directorFilter = req.getParameter("director");
		UserManager userManager = new UserManager(req);
		
		List<Film> filmList = filmService.orderByReleaseDate(filmService.getAllFilms());
		List<String> genreList = filmService.getGenres();
		
		if (genreFilter != null) {
			filmList = filmService.filterByGenre(filmList, genreFilter);
		}
		
		if (directorFilter != null) {
			if (userManager.existsUser()) {
				filmList = filmService.filterByDirector(filmList, directorFilter);
			}
			else {
				req.setAttribute("directorFilterError", "unauthorized");
			}
		}
		
		req.setAttribute("filmList", filmList);
		req.setAttribute("genreList", genreList);
		req.getRequestDispatcher("/WEB-INF/jsp/filmList.jsp").forward(req, resp);
	}
}
