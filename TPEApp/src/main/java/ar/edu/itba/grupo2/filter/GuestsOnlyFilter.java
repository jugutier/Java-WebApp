package ar.edu.itba.grupo2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.service.UserManager;

public class GuestsOnlyFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) resp;

		UserManager userManager = new UserManager((HttpServletRequest)req);
		
		if (userManager.existsUser()) {
			response.sendRedirect(response.encodeRedirectURL("home"));
		}
		else {
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

}
