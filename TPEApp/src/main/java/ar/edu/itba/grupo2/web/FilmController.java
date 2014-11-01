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
import ar.edu.itba.grupo2.domain.film.UserIsntAdminException;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.command.CommentForm;
import ar.edu.itba.grupo2.web.command.FilmForm;
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
	public ModelAndView filmDetails(HttpSession session, @RequestParam(value = "id", required=false) Film film) {
		ModelAndView mav = new ModelAndView();
		
		//Film film = filmRepo.get(id);
		
		mav.addObject("commentList", film.getComments());
		mav.addObject("film", film);
		
		if(isLoggedIn(session)) {
			boolean userCanComment = film.userCanComment(getLoggedInUser(session));//filmRepo.userCanComment(film, user);
			mav.addObject("userCanComment", userCanComment);
			if(userCanComment){
				mav.addObject("commentForm", new CommentForm());
			}
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView editFilmDetails(HttpSession session, @RequestParam(value = "id", required=false) Film film) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("film", film);
		
		mav.setViewName("editFilmDetails");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String confirmEdition(HttpSession session,FilmForm filmForm){
		User user = getLoggedInUser(session);
		if(user.isAdmin()){
			System.out.println("isAdmin");
		}
		return "redirect:filmList";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String filmDetails(HttpSession session, CommentForm commentForm, Errors errors, @RequestParam(value = "id", required=false) Film film) {
		
		commentValidator.validate(commentForm, errors);
		if (errors.hasErrors()) {
			errors.rejectValue("comment", "required");
			session.setAttribute("errors", errors);
			//return null;
			return "redirect:filmDetails?id=" + commentForm.getFilmId();
		}
		
		User user = getLoggedInUser(session);
		Comment newComment = new Comment.Builder()
								.user(user)
								.film(film)
								.text(commentForm.getText())
								.rate(commentForm.getRating())
								.creationDate(new Date())
								.build();
		
		try {
			film.addComment(newComment);
		}
		catch(UserCantCommentException e) {
			
		}
		
		return "redirect:filmDetails?id=" + commentForm.getFilmId();
	}
	@RequestMapping(method=RequestMethod.POST)
	public String removeFilm(HttpSession session,@RequestParam(value = "id", required=true) Integer id){
		User user = getLoggedInUser(session);
		if(user.isAdmin()){
			Film film = filmRepo.get(id);
			filmRepo.delete(film);
		}else{
			throw new UserIsntAdminException();
		}
		return "redirect:filmList";
		
	}
	@RequestMapping(method=RequestMethod.POST)
	public String removeCommentFromFilm(
			HttpSession session,
			CommentForm commentForm
			) {
		Film film = filmRepo.get(commentForm.getFilmId());
		
		
		try {
			film.removeComment(commentForm.getComment());
		}
		catch(UserIsntAdminException e) {
			
		}
		
		return "redirect:filmDetails?id=" + commentForm.getFilmId();
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
