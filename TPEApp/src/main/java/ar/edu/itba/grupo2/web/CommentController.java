package ar.edu.itba.grupo2.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.comment.CommentRepo;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Controller
@RequestMapping(value = "comment")
public class CommentController extends BaseController {

	private final CommentRepo commentRepo;

	@Autowired
	public CommentController(UserRepo userRepo, CommentRepo commentRepo) {
		super(userRepo);
		this.commentRepo = commentRepo;
	}

	@RequestMapping(value = "{id}/report", method = RequestMethod.GET)
	public String report(HttpSession session,
			@PathVariable(value = "id") Comment comment) {
		User user = getLoggedInUser(session);
		
		comment.report(user);
		return "redirect:../../film/" + comment.getFilm().getId() + "/details";
	}

	@RequestMapping(value = "reported", method = RequestMethod.GET)
	public ModelAndView reportedList(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		List<Comment> commentList = commentRepo.getAllReported();
		mav.addObject("commentList", commentList);

		mav.setViewName("reportList");
		return mav;
	}

	@RequestMapping(value = "{id}/discardReports", method = RequestMethod.GET)
	public String discardReports(HttpSession session,
			@PathVariable(value = "id") Comment comment) {
		authenticateAdmin(session);
		comment.discardReports();

		return "redirect:../reported";
	}

	@RequestMapping(value = "{id}/rate", method = RequestMethod.POST)
	public String rateComment(HttpSession session,
			@PathVariable(value = "id") Comment comment,
			@RequestParam(value = "rating") int rating) {
		User user = getLoggedInUser(session);
		comment.rate(user, rating);
		
		return "redirect:../../film/" + comment.getFilm().getId() + "/details";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String removeComment(HttpSession session,
			@RequestParam(value = "film", required = false) Film film,
			@RequestParam(value = "id") Comment comment,
			@RequestParam(value = "fromPage") String fromPage) {
		
		authenticateAdmin(session);
		film.removeComment(comment);

		return "redirect:" + fromPage;
	}

}
