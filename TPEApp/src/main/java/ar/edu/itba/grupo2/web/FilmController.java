package ar.edu.itba.grupo2.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.dao.exceptions.FilmNotFoundException;
import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.Film;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.FilmService;
import ar.edu.itba.grupo2.service.impl.FilmServiceImpl;

@Controller
public class FilmController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView();
		
		FilmService filmService = FilmServiceImpl.getInstance();

		List<Film> filmList = filmService.getAllFilms();	
		
		List<Film> topfive = filmService.filterTopFilms(filmList, 5);
		mav.addObject("topfive", topfive);
		mav.addObject("latest", filmService.filterRecentlyAdded(filmList, 5));
		mav.addObject("newReleases",
				filmService.filterNewReleases(filmList, 7));
		
		mav.setViewName("welcome");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView filmDetails(@RequestParam(value = "id", required=false) Integer id) {
		ModelAndView mav = new ModelAndView();
		
		FilmService filmService = FilmServiceImpl.getInstance();
		Film film = null;
		try {
			film = filmService.getFilmById(id);
			
			//User user = (User)req.getSession(false).getAttribute("user");
			
			final List<Comment> commentList = filmService.getCommentsForFilm(film);
			
			mav.addObject("commentList", commentList);
			mav.addObject("film", film);
			
			/*if(user != null) {
				boolean userCanComment = filmService.userCanComment(film, user);
				mav.addObject("userCanComment", userCanComment);
			}*/
		} catch (FilmNotFoundException e) {
			film = null;
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
}
