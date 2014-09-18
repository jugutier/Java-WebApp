package ar.edu.itba.grupo2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FromPageFilter implements Filter {

	private String getCurrentPath(HttpServletRequest req) {
		StringBuffer stringBuffer = new StringBuffer();
		String queryString = req.getQueryString();
		stringBuffer.append(req.getRequestURI());
		
		if (queryString != null && !queryString.isEmpty()) {
			stringBuffer.append("?");
			stringBuffer.append(queryString);
		}
		
		return stringBuffer.toString();
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		
		req.setAttribute("fromPage", getCurrentPath(request));
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

}
