package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserNotAdminException;
import ar.edu.itba.grupo2.domain.user.UserNotAuthenticatedException;
import ar.edu.itba.grupo2.domain.user.UserNotFollowedException;
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
	
	protected User getLoggedInUser(HttpSession session) throws UserNotAuthenticatedException {
		User user = null;
		
		if (isLoggedIn(session)) {
			user = userRepo.get((Integer)session.getAttribute(USER_ID));
		}
		else {
			throw new UserNotAuthenticatedException();
		}
		
		return user;
	}
	
	protected User getLoggedInAdmin(HttpSession session) throws UserNotAuthenticatedException, UserNotAdminException {
		User user = getLoggedInUser(session);
		
		if (!user.isAdmin()) {
			throw new UserNotAdminException();
		}
		
		return user;
	}
	
	protected void authenticateAdmin(HttpSession session) throws UserNotAuthenticatedException, UserNotAdminException {
		getLoggedInAdmin(session);
	}
	
	protected void logOut(HttpSession session) {
		session.removeAttribute(USER_ID);
	}
	
	@ExceptionHandler({UserNotAuthenticatedException.class})
	public String unauthenticatedUserError(Exception e) {
		return "error/unauthenticated-user-error";
	}
	
	@ExceptionHandler(UserNotAdminException.class)
	public String permissionDeniedError(Exception e) {
		return "error/userNotAdmin";
	}

}
