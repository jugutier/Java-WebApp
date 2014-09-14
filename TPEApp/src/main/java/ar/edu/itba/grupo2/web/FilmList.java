package ar.edu.itba.grupo2.web;

import java.io.IOException;
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
public class FilmList extends HttpServlet{
	
	FilmManagerDAO fm;
	
	@Override
	public void init() throws ServletException{
		super.init();
		fm = MemoryFilmManager.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		final Film film = new Film.Builder()
				.name("JG")
				.director("PURI")
				.releaseDate(new Date())
				.genre("De tiros")
				.description("Chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken chicken")
				.length(20)
				.build();
		fm.saveFilm(film);
		
		
		List<Film> film_list = fm.getAllFilms();
		
		req.setAttribute("filmList", film_list);
		req.getRequestDispatcher("/WEB-INF/jsp/filmList.jsp").forward(req, resp);
	}
}
