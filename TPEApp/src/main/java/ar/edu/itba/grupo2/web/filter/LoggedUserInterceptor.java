package ar.edu.itba.grupo2.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.web.session.UserManager;

public class LoggedUserInterceptor extends HandlerInterceptorAdapter {
	
	private final UserManager userManager;
	
	@Autowired
	public LoggedUserInterceptor(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler) throws Exception {
		
		User user = userManager.getUser();
		
		req.setAttribute("loggedInUser", user);
		
		return true;
	}
}
