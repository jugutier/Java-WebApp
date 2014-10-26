package ar.edu.itba.grupo2.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.command.CommentForm;
import ar.edu.itba.grupo2.web.command.validator.CommentFormValidator;

@Controller
public class FilmController extends BaseController {
	
	private final FilmRepo filmRepo;
	private CommentFormValidator commentValidator;
	
	@Autowired
	public FilmController(FilmRepo filmRepo, UserRepo userRepo, CommentFormValidator commentValidator) {
		super(userRepo);
		this.filmRepo = filmRepo;
		this.commentValidator = commentValidator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("topfive", filmRepo.getTop(5));
		mav.addObject("latest", filmRepo.getLatest(5));
		mav.addObject("newReleases",
				filmRepo.getNewests(7));
		
		mav.setViewName("welcome");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView filmDetails(HttpSession session, @RequestParam(value = "id", required=false) Integer id) {
		ModelAndView mav = new ModelAndView();
		
		Film film = null;
		film = filmRepo.get(id);
		
		mav.addObject("commentList", film.getComments());
		mav.addObject("film", film);
		
		mav.addObject("comment", new CommentForm());
		
		if(isLoggedIn(session)) {
			boolean userCanComment = film.userCanComment(getLoggedInUser(session));//filmRepo.userCanComment(film, user);
			mav.addObject("userCanComment", userCanComment);
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addCommentToFilm(
			HttpSession session,
			CommentForm commentForm,
			Errors errors,
			@RequestParam(value = "filmId") int id,
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "rating") Integer rating) {
		
		commentValidator.validate(commentForm, errors);
		if (errors.hasErrors()) {
			return "redirect:filmDetails?id=" + id;
		}
		
		User user = getLoggedInUser(session);
		Comment newComment = new Comment.Builder()
								.user(user)
								.film(filmRepo.get(id))
								.text(comment)
								.rate(rating)
								.creationDate(new Date())
								.build();
		
		try {
			filmRepo.get(id).addComment(newComment);
		}
		catch(UserCantCommentException e) {
			
		}
		
		return "redirect:filmDetails?id=" + id;
	}
	
	@RequestMapping(value = "filmList", method=RequestMethod.GET)
	public ModelAndView list(HttpSession session, @RequestParam(value = "genre", required=false) Genre genre, @RequestParam(value = "director", required=false) String director) {
		ModelAndView mav = new ModelAndView();
		
		List<Film> filmList = null;
		List<Genre> genreList = filmRepo.getGenres();
		
		if (genre != null) {
			filmList = filmRepo.getFromGenre(genre);
		}
		else{
			filmList = filmRepo.getByReleaseDate();
		}
		
		if (director != null) {
			if (isLoggedIn(session)) {
				filmList = filmRepo.getFromDirector(director);
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
