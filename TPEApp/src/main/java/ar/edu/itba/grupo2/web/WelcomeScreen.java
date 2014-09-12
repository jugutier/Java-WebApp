package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.FilmManager;
import ar.edu.itba.grupo2.dao.memory.MemoryFilmManager;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.utils.ConnectionUtilities;
import ar.edu.itba.it.paw.model.API;

@SuppressWarnings("serial")
public class WelcomeScreen extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		API.createBogusDatabase();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		FilmManager fm = MemoryFilmManager.getInstance();
		for (int i = 0; i < 4; i++) {
			Film film = new Film.Builder()
			.name("JG")
			.director("PURI")
			.releaseDate(new Date())
			.build();
			fm.saveFilm(film);
		}		
		Connection c = ConnectionUtilities.getInstance().getConnection();
		ConnectionUtilities.getInstance().testQuery(c);
		List<Film> filmList = fm.getAllFilms();
		req.setAttribute("topfive", filmList.subList(0, 4));
		req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
	}
}
