package ar.edu.itba.grupo2.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmNotFoundException;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.service.FilmService;
import ar.edu.itba.grupo2.web.session.UserManager;

@Controller
public class FilmController extends BaseController {
	
	private final FilmService filmService;
	private final UserManager userManager;
	
	@Autowired
	public FilmController(FilmService filmService, UserManager userManager) {
		this.filmService = filmService;
		this.userManager = userManager;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView();
		
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
		
		Film film = null;
		try {
			film = filmService.getFilmById(id);
			User user = userManager.getUser();
			
			final List<Comment> commentList = filmService.getCommentsForFilm(film);
			
			mav.addObject("commentList", commentList);
			mav.addObject("film", film);
			
			if(user != null) {
				boolean userCanComment = filmService.userCanComment(film, user);
				mav.addObject("userCanComment", userCanComment);
			}
		} catch (FilmNotFoundException e) {
			film = null;
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
	
	@RequestMapping(value = "filmList", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "genre", required=false) String genre, @RequestParam(value = "director", required=false) String director) {
		ModelAndView mav = new ModelAndView();
		
		List<Film> filmList = filmService.orderByReleaseDate(filmService.getAllFilms());
		List<String> genreList = filmService.getGenres();
		
		if (genre != null) {
			filmList = filmService.filterByGenre(filmList, genre);
		}
		
		if (director != null) {
			if (userManager.existsUser()) {
				filmList = filmService.filterByDirector(filmList, director);
			}
			else {
				mav.addObject("directorFilterError", "unauthorized");
			}
		}
		
		mav.addObject("filmList", filmList);
		mav.addObject("genreList", genreList);
		
		mav.setViewName("filmList");
		
		return mav;
	}
}
