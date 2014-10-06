package ar.edu.itba.grupo2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.web.session.UserManager;
import ar.edu.itba.grupo2.web.session.UserManagerImpl;

public class LoggedUserFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

		UserManager userManager = new UserManagerImpl((HttpServletRequest)req);
		User user = userManager.getUser();
		
		req.setAttribute("loggedInUser", user);
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

}
