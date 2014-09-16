package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.service.FilmService;
import ar.edu.itba.it.paw.model.Utilities;

@SuppressWarnings("serial")
public class FilmDetails extends BaseWebServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		super.doGet(req, resp);
		FilmService filmService = FilmService.getInstance();
		final int id = Integer.valueOf(Utilities.getQueryMap(req.getQueryString()).get("id"));
		
		try{
			final Film film = filmService.getFilmById(id);			
			final List<Comment> commentList = filmService.getCommentsForFilm(film);
			
			req.setAttribute("commentList", commentList);
			req.setAttribute("film", film);
		}
		catch(FilmNotFoundException e){
			
		}
		
		
		req.getRequestDispatcher("/WEB-INF/jsp/filmDetails.jsp").forward(req, resp);
	}
}
