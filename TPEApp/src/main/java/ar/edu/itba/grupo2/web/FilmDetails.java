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
			
			if(!filmService.userCanComment(film, user)){
				req.setAttribute("userCanComment", false);
			}else{
				req.setAttribute("userCanComment", true);
			}
						
		}
		catch(FilmNotFoundException e){
			
		}
		
		req.getRequestDispatcher("/WEB-INF/jsp/filmDetails.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		FilmService filmService = FilmService.getInstance();
		Film film;
		User user = (User)req.getSession(false).getAttribute("user");
		
		try {		
			film = filmService.getFilmById(Integer.parseInt(req.getParameter("id")));
			if(filmService.userCanComment(film, user)){
				final Comment comment = new Comment.Builder()
					.user(user)
					.text(req.getParameter("comment"))
					.rate(Integer.parseInt(req.getParameter("rating")))
					.film(film)
					.build();
				filmService.addCommentToFilm(film, comment);
				System.out.println(film + " - " + comment);
				req.setAttribute("newComment", "true");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FilmNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.doGet(req, resp);
	}
}
