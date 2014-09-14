package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.dao.memory.MemoryFilmManager;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.it.paw.model.Utilities;

@SuppressWarnings("serial")
public class FilmDetails extends HttpServlet{
	
	FilmManagerDAO fm;
	
	@Override
	public void init() throws ServletException{
		super.init();
		fm = MemoryFilmManager.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		final int id = Integer.valueOf(Utilities.getQueryMap(req.getQueryString()).get("id"));
		
		try{
			final Film film = fm.getFilmById(id);			
			
			final List<Comment> commentList = fm.getCommentsForFilm(film);
			
			req.setAttribute("commentList", commentList);
			req.setAttribute("film", film);
		}
		catch(FilmNotFoundException e){
			
		}
		
		
		req.getRequestDispatcher("/WEB-INF/jsp/filmDetails.jsp").forward(req, resp);
	}
}
