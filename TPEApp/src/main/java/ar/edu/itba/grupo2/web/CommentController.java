package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Controller
@RequestMapping(value = "comment")
public class CommentController extends BaseController {

	@Autowired
	public CommentController(UserRepo userRepo) {
		super(userRepo);
	}
	
	@RequestMapping(value = "{id}/report", method = RequestMethod.GET)
	public String report(HttpSession session, @PathVariable(value = "id") Comment comment) {
		comment.report(getLoggedInUser(session));
		return "redirect:../../film/filmDetails?id=" + comment.getFilm().getId();
	}

}
