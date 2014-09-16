package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserService;

@SuppressWarnings("serial")
public class BaseWebServlet extends HttpServlet{
	
	protected void setLoginInformation(HttpServletRequest req, HttpServletResponse resp) {
		//TODO: get a User reference here, and pass it as argument
		UserService userService = UserService.getInstance();
		
		//User user = userService.getLoggedInUser();
		
		//req.setAttribute("loggedInUser", user);
		req.setAttribute("fromPage", getCurrentPath(req));
	}
	
	protected String getCurrentPath(HttpServletRequest req) {
		StringBuffer stringBuffer = new StringBuffer();
		String queryString = req.getQueryString();
		stringBuffer.append(req.getRequestURI());
		
		if (queryString != null && !queryString.isEmpty()) {
			stringBuffer.append("?");
			stringBuffer.append(queryString);
		}
		
		return stringBuffer.toString();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		setLoginInformation(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		setLoginInformation(req, resp);
	}
}
