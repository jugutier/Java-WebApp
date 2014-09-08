package ar.edu.itba.it.paw.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.UserManager;

public class WelcomeScreenFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		UserManager userManager = new UserManager(request);
		
		if (userManager.existsUser()){
			response.sendRedirect(response.encodeRedirectURL("listHotels"));
			return;
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
