package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;

@SuppressWarnings("serial")
public class WelcomeScreen extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		API.createBogusDatabase();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
	}
}
