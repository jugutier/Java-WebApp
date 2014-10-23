package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Controller
public class BaseController {
	
	protected UserRepo userRepo;
	
	private final String USER_ID = "userId";
	
	@Autowired
	public BaseController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	protected void setLoggedInUser(HttpSession session, User user) {
		session.setAttribute(USER_ID, user.getId());
	}
	
	protected boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(USER_ID) != null;
	}
	
	protected User getLoggedInUser(HttpSession session) {
		return userRepo.get((Integer)session.getAttribute(USER_ID));
	}
	
	protected void logOut(HttpSession session) {
		session.removeAttribute(USER_ID);
	}

}
