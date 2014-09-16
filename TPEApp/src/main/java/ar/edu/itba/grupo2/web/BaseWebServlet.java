package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BaseWebServlet extends HttpServlet{
	
	protected void setLoginInformation(HttpServletRequest req, HttpServletResponse resp) {
		//TODO: get a User reference here, and pass it as argument
//		User u = new User().
//		u.name = "pipi";
//		
//		req.setAttribute("loggedInUser", u);
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
