package ar.edu.itba.grupo2.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import ar.edu.itba.grupo2.model.User;

@Controller
public class BaseController extends WebApplicationObjectSupport {
	
	protected void setLoggedInUser(HttpServletRequest req, User user) {
		req.getSession().setAttribute("userId", user.getId());
	}
	
	protected boolean isLoggedIn(HttpServletRequest req) {
		return req.getSession().getAttribute("userId") != null;
	}
	
	protected User getLoggedInUser(HttpServletRequest req) {
		return (User) req.getAttribute("user");
	}

}
