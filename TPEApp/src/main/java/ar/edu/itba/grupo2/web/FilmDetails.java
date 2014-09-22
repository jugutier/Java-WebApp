package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.rmi.UnexpectedException;
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
import ar.edu.itba.grupo2.service.impl.FilmServiceImpl;

@SuppressWarnings("serial")
public class FilmDetails extends HttpServlet{
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		FilmService filmService = FilmServiceImpl.getInstance();
		String strId = req.getParameter("id");
		Film film = null;
		if (strId != null) {
			try {
				int id = Integer.valueOf(req.getParameter("id"));
				
					film = filmService.getFilmById(id);
				
				User user = (User)req.getSession(false).getAttribute("user");
				
				final List<Comment> commentList = filmService.getCommentsForFilm(film);
				
				req.setAttribute("commentList", commentList);
				req.setAttribute("film", film);
				
				if(user != null) {
					boolean userCanComment = filmService.userCanComment(film, user);
					req.setAttribute("userCanComment", userCanComment);
				}
			} catch (FilmNotFoundException e) {
				film = null;
			}
	
		}
		
		req.getRequestDispatcher("/WEB-INF/jsp/filmDetails.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		FilmService filmService = FilmServiceImpl.getInstance();
		Film film;
		User user = (User)req.getSession(false).getAttribute("user");
		if (req.getParameter("comment") != ""){
			try {		
				film = filmService.getFilmById(Integer.parseInt(req.getParameter("id")));
				if(filmService.userCanComment(film, user)){
					final Comment comment = new Comment.Builder()
						.user(user)
						.text(req.getParameter("comment"))
						.rate(Integer.parseInt(req.getParameter("rating")))
						.film(film)
						.build();
					filmService.addComment(comment);
					req.setAttribute("newComment", true);
				}
			} catch (FilmNotFoundException e) {
				throw new UnexpectedException("Internal Error");
			}
		}else{
			req.setAttribute("error", "NoComment");
		}	
		
		//resp.sendRedirect(arg0);
		this.doGet(req, resp);
	}
}
