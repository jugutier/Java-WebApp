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
		Film film;
		User user = (User)req.getSession(false).getAttribute("user");
		
		try {			
			film = fm.getFilmById(Integer.parseInt(req.getParameter("id")));
			// TODO Add other requirements for comments
			if(film.isReleased()) {
				final Comment comment = new Comment.Builder()
					.text(req.getParameter("comment"))
					.user(user)
					.rate(Integer.parseInt(req.getParameter("rating")))
					.film(film)
					.build();
				fm.addCommentToFilm(film, comment);
				System.out.println(film + " - " + comment);
				req.setAttribute("newComment", "true");
			} else {
				// TODO throw new unreleased film exception
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
