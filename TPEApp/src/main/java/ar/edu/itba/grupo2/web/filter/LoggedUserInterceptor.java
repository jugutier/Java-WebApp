package ar.edu.itba.grupo2.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;

public class LoggedUserInterceptor extends HandlerInterceptorAdapter {
	
	private final UserRepo userRepo;
	
	@Autowired
	public LoggedUserInterceptor(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler) throws Exception {
		
		Integer userId = (Integer)req.getSession().getAttribute("userId");
		User user = null;
		
		if (userId != null)
			user = userRepo.get(userId);
		
		req.setAttribute("loggedInUser", user);
		
		return true;
	}
}
