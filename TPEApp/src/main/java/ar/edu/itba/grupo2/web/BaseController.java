package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpServletRequest;

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
	
	protected void setLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute(USER_ID, user.getId());
	}
	
	protected boolean isLoggedIn(HttpServletRequest req) {
		return req.getSession().getAttribute(USER_ID) != null;
	}
	
	protected User getLoggedInUser(HttpServletRequest req) {
		return userRepo.get((Integer)req.getSession().getAttribute(USER_ID));
	}
	
	protected void logOut(HttpServletRequest req) {
		req.getSession().removeAttribute(USER_ID);
	}

}
