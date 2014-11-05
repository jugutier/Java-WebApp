package ar.edu.itba.grupo2.web;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserNotAdminException;
import ar.edu.itba.grupo2.domain.user.UserNotAuthenticatedException;
import ar.edu.itba.grupo2.domain.user.UserRepo;
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
	
	@RequestMapping(value = "{id}/details", method=RequestMethod.GET)
	public ModelAndView filmDetails(HttpSession session, @PathVariable(value = "id") Film film) {
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
	

	@RequestMapping(value = "{id}/details", method=RequestMethod.POST)
	public ModelAndView addComment(HttpSession session, CommentForm commentForm, Errors errors, @PathVariable(value = "id") Film film, @RequestParam(value = "fromPage") String fromPage) {
		ModelAndView mav = new ModelAndView();
		User user = getLoggedInUser(session);		
		
		if (user == null) {
			throw new UserNotAuthenticatedException();
		}
		
		mav.addObject("film", film);
		mav.setViewName("filmDetails");
		
		commentValidator.validate(commentForm, errors);
		if (errors.hasErrors()) {
			mav.addObject("commentList", film.getCommentsForUser(user));
			if(isLoggedIn(session)) {
				boolean userCanComment = film.userCanComment(user);
				mav.addObject("userCanComment", userCanComment);
				if(userCanComment){
					mav.addObject("commentForm", new CommentForm());
				}
			}
			return mav;
		}
		
		Comment newComment = new Comment.Builder()
								.user(user)
								.film(film)
								.text(commentForm.getText())
								.rate(commentForm.getRating())
								.creationDate(new Date())
								.build();
		
		try {
			film.addComment(newComment);
			user.addComment(newComment);
			//mav.addObject("userCanComment", false);
		}
		catch(UserCantCommentException e) {
			
		}
		
		//mav.addObject("commentList", film.getCommentsForUser(user));
		mav.setViewName("redirect:details");
		return mav;
	}

	@RequestMapping(value = "{id}/edit", method=RequestMethod.GET)
	public ModelAndView editFilm(HttpSession session, @PathVariable(value = "id") Film film) {
		ModelAndView mav = new ModelAndView();
		List<Genre> genres = filmRepo.getGenres();
		
		mav.addObject("genreList", genres);
		mav.addObject("film", film);
		mav.addObject("filmForm", new FilmForm(film));
		
		mav.setViewName("editFilm");
		
		return mav;
	}
	
	@RequestMapping(value = "{id}/edit", method=RequestMethod.POST)
	public String editFilmSubmit(HttpSession session, Model model, @ModelAttribute @Valid FilmForm filmForm, Errors errors, @RequestParam(value = "movieImage") MultipartFile movieImage, @RequestParam(value = "deleteImage", required = false) boolean deleteImage){
		
		if (!isLoggedIn(session) || !getLoggedInUser(session).isAdmin() ) {
			return "redirect:../../welcome";
		}
		
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = null;
		Film film = filmRepo.get(filmForm.getId());
		
		filmValidator.validate(filmForm, errors);
		
		try {
			releaseDate = outputDateFormat.parse(filmForm.getReleaseDate());
		} catch (ParseException e) {
			errors.rejectValue("releaseDate", "invalid");
		}
		
		if (!errors.hasErrors()) {
			buildFilm(film, filmForm, movieImage, releaseDate, deleteImage);
		}
		else {
			model.addAttribute("genreList", filmRepo.getGenres());
			return "editFilm";
		}
			
		return "redirect:details";
	}
	
	@RequestMapping(value = "add", method=RequestMethod.GET)
	public ModelAndView addFilm(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Genre> genres = filmRepo.getGenres();
		
		mav.addObject("genreList", genres);
		mav.addObject("filmForm", new FilmForm());
		
		mav.setViewName("addFilm");
		
		return mav;
	}
	
	@RequestMapping(value = "add", method=RequestMethod.POST)
	public String addFilmSubmit(HttpSession session, Model model, FilmForm filmForm, Errors errors, @RequestParam(value = "movieImage") MultipartFile movieImage){

		if (!isLoggedIn(session) || !getLoggedInUser(session).isAdmin() ) {
			return "redirect:../welcome";
		}
		
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = null;
		Film film = new Film.Builder().creationDate(new Date()).build();
		
		filmValidator.validate(filmForm, errors);
		
		try {
			releaseDate = outputDateFormat.parse(filmForm.getReleaseDate());
		} catch (ParseException e) {
			errors.rejectValue("releaseDate", "invalid");
		}

		if (!errors.hasErrors()) {
			buildFilm(film, filmForm, movieImage, releaseDate, false);
			filmRepo.save(film);
		}
		else {
			model.addAttribute("genreList", filmRepo.getGenres());
			model.addAttribute("film", film);
			return "addFilm";
		}
		
		return "redirect:list";
	}
	
	private void buildFilm(Film film, FilmForm filmForm, MultipartFile movieImage, Date releaseDate, boolean deleteImage) {
		film.setName(filmForm.getName());
		film.setDirector(filmForm.getDirector());
		film.setLength(filmForm.getLength());
		film.setGenres(filmForm.getGenres());
		film.setDescription(filmForm.getDescription());
		film.setReleaseDate(releaseDate);
		
		if (deleteImage) {
			film.setFilmImage(null);
		}
		else if (movieImage.getSize() > 0) {
			String name = movieImage.getName();
			String contentType = movieImage.getContentType();
			int length = (int) movieImage.getSize();
			
			byte[] imageData = new byte[length];

			try {
				InputStream fileInputStream = filmForm.getMovieImage().getInputStream();
				MovieImage mi = film.getMovieImage();
				fileInputStream.read(imageData);
				fileInputStream.close();
				if (mi == null) {
					film.setFilmImage(new MovieImage(name, contentType, length,
							imageData, film));
				}
				else {
					mi.setName(name);
					mi.setContentType(contentType);
					mi.setLength(length);
					mi.setContent(imageData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(method=RequestMethod.POST)
	public String removeFilm(HttpSession session,@RequestParam(value = "id", required=true) Integer id){
		User user = getLoggedInUser(session);
		
		if (user == null) {
			throw new UserNotAuthenticatedException();
		}
		else if(!user.isAdmin()){
			throw new UserNotAdminException();
		}
		
		Film film = filmRepo.get(id);
		filmRepo.delete(film);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "list", method=RequestMethod.GET)
	public ModelAndView list(HttpSession session, @RequestParam(value = "genre", required=false) Genre genre, @RequestParam(value = "director", required=false) String director) {
		ModelAndView mav = new ModelAndView();
		
		List<Film> filmList = null;
		List<Genre> genreList = filmRepo.getGenres();
		
		filmList = filmRepo.getFiltered(genre, director);
		
		/*if (genre != null) {
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
		}*/
		
		mav.addObject("filmList", filmList);
		mav.addObject("genreList", genreList);
		
		mav.setViewName("filmList");
		
		return mav;
	}
}