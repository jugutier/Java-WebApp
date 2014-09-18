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

public class LoggedUserFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

		req.setAttribute("loggedInUser", (User)((HttpServletRequest) req).getSession().getAttribute("user"));
		chain.doFilter(req, res);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

}
