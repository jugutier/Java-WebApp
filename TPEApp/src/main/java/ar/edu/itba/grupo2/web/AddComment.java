package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.dao.FilmManagerDAO;
import ar.edu.itba.grupo2.dao.PSQLImpl.FilmManagerPSQLImpl;
import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.FilmService;

@SuppressWarnings("serial")
public class AddComment extends HttpServlet {
	
	private FilmManagerDAO fm;
	
	@Override
	public void init() throws ServletException{
		super.init();
		fm = FilmManagerPSQLImpl.getInstance();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		FilmService filmService = FilmService.getInstance();
		Film film;
		User user = (User)req.getSession(false).getAttribute("user");
		
		try {			
			film = fm.getFilmById(Integer.parseInt(req.getParameter("id")));
			// TODO Add other requirements for comments
			if(filmService.userHasCommentedFilm(film, user)) {
				resp.sendRedirect("filmDetails?id="+req.getParameter("id"));
				return ;
			}
			if(film.isReleased() || user.isVip()) {
				final Comment comment = new Comment.Builder()
					.user(user)
					.text(req.getParameter("comment"))
					.rate(Integer.parseInt(req.getParameter("rating")))
					.film(film)
					.build();
				fm.addCommentToFilm(film, comment);
				System.out.println(film + " - " + comment);
				req.setAttribute("newComment", "true");
			} else {
				// TODO throw new unreleased exception (or add error and make a redirect)
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FilmNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.sendRedirect("filmDetails?id="+req.getParameter("id"));
	}
}
