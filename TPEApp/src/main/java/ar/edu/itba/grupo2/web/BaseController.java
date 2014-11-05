package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserNotAdminException;
import ar.edu.itba.grupo2.domain.user.UserNotFollowedException;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Controller
public class BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
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
		User user = null;
		if (isLoggedIn(session)) {
			user = userRepo.get((Integer)session.getAttribute(USER_ID));
		}
		return user;
	}
	
	protected void logOut(HttpSession session) {
		session.removeAttribute(USER_ID);
	}
	
	@ExceptionHandler({UserNotFollowedException.class})
	public String unauthenticatedUserError() {
		return "error/unauthenticated-user-error";
	}
	
	@ExceptionHandler({UserNotAdminException.class})
	public String permissionDeniedError() {
		return "error/userNotAdmin";
	}
	
	@ExceptionHandler({Exception.class})
	public String generalError(Exception exception) {
		logger.error(exception.toString());
		return "error/dispatch-error";
	}

}
