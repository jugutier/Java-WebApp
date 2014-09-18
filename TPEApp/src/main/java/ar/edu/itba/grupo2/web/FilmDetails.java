package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.FilmService;

@SuppressWarnings("serial")
public class FilmDetails extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		FilmService filmService = FilmService.getInstance();
		final int id = Integer.valueOf(req.getParameter("id"));
		
		try{
			final Film film = filmService.getFilmById(id);
			User user = (User)req.getSession(false).getAttribute("user");
			
			System.out.println("GENRE:"+film.getGenre());
			final List<Comment> commentList = filmService.getCommentsForFilm(film);
			
			req.setAttribute("commentList", commentList);
			req.setAttribute("film", film);
			
			if(filmService.userHasCommentedFilm(film, user) 
					|| (!film.isReleased() && !user.isVip())) {
				req.setAttribute("userCantComment", true);
			}
		}
		catch(FilmNotFoundException e){
			
		}
		
		
		
		
		req.getRequestDispatcher("/WEB-INF/jsp/filmDetails.jsp").forward(req, resp);
	}
}
