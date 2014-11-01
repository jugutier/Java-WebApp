package ar.edu.itba.grupo2.web;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;
import ar.edu.itba.grupo2.domain.film.UserIsntAdminException;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.command.CommentForm;
import ar.edu.itba.grupo2.web.command.FilmForm;
import ar.edu.itba.grupo2.web.command.validator.CommentFormValidator;
import ar.edu.itba.grupo2.web.command.validator.FilmFormValidator;

@Controller
@RequestMapping(value = "film")
public class FilmController extends BaseController {
	
	private final FilmRepo filmRepo;
	private final CommentFormValidator commentValidator;
	private final FilmFormValidator filmValidator;
	
	@Autowired
	public FilmController(FilmRepo filmRepo, UserRepo userRepo, CommentFormValidator commentValidator, FilmFormValidator filmValidator) {
		super(userRepo);
		this.filmRepo = filmRepo;
		this.commentValidator = commentValidator;
		this.filmValidator = filmValidator;
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
	public ModelAndView filmDetails(HttpSession session, @RequestParam(value = "id", required=true) Film film) {
		ModelAndView mav = new ModelAndView();		
		User user = getLoggedInUser(session);		
		mav.addObject("commentList", film.getCommentsForUser(user));
		mav.addObject("film", film);
		
		if(isLoggedIn(session)) {
			boolean userCanComment = film.userCanComment(user);
			mav.addObject("userCanComment", userCanComment);
			if(userCanComment){
				mav.addObject("commentForm", new CommentForm());
			}
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
	

	@RequestMapping(value = "{id}/edit", method=RequestMethod.GET)
	public ModelAndView editFilm(HttpSession session, @PathVariable(value = "id") Film film) {
		ModelAndView mav = new ModelAndView();
		List<Genre> genres = filmRepo.getGenres();
		
		mav.addObject("film", film);
		mav.addObject("genreList", genres);
		mav.addObject("filmForm", new FilmForm());
		
		mav.setViewName("editFilm");
		
		return mav;
	}
	
	@RequestMapping(value = "{id}/edit", method=RequestMethod.POST)
	public String editFilmSubmit(HttpSession session, Model model, FilmForm filmForm, Errors errors, @RequestParam(value = "movieImage") MultipartFile movieImage){
		User user = getLoggedInUser(session);
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = null;
		Film film = filmRepo.get(filmForm.getId());
		
		filmValidator.validate(filmForm, errors);
		
		try {
			releaseDate = outputDateFormat.parse(filmForm.getReleaseDate());
		} catch (ParseException e) {
			errors.rejectValue("releaseDate", "invalid");
		}
		
		if(user.isAdmin()){
			if (!errors.hasErrors()) {
				film.setName(filmForm.getName());
				film.setDirector(filmForm.getDirector());
				film.setLength(filmForm.getLength());
				film.setGenres(filmForm.getGenres());
				film.setDescription(filmForm.getDescription());
				film.setReleaseDate(releaseDate);
				
				if (movieImage.getSize() > 0) {
					String name = movieImage.getName();
					String contentType = movieImage.getContentType();
					int length = (int) movieImage.getSize();
					
					byte[] imageData = new byte[length];

					try {
						InputStream fileInputStream = filmForm.getMovieImage().getInputStream();
						fileInputStream.read(imageData);
						fileInputStream.close();
						film.setFilmImage(new MovieImage(name, contentType, length,
								imageData, film));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else {
				model.addAttribute("genreList", filmRepo.getGenres());
				model.addAttribute("film", film);
				return "editFilm";
			}
		}
		else {
			return "redirect:../../welcome";
		}
		return "redirect:../filmList";
	}	

	@RequestMapping(method=RequestMethod.POST)
	public String filmDetails(HttpSession session, CommentForm commentForm, Errors errors, @RequestParam(value = "id", required=false) Film film) {
		
		commentValidator.validate(commentForm, errors);
		if (errors.hasErrors()) {
			errors.rejectValue("text", "required");
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

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView editFilmDetails(HttpSession session, @RequestParam(value = "id", required=false) Integer id) {
		ModelAndView mav = new ModelAndView();
		
		Film film = filmRepo.get(id);
		
		mav.addObject("film", film);
		
		mav.setViewName("editFilmDetails");
		
		return mav;
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