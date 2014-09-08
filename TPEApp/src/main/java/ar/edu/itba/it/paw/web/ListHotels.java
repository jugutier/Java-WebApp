package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.Hotel;

@SuppressWarnings("serial")
public class ListHotels extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		API.createBogusDatabase();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		List<Hotel> list = API.getAllHotels();
		
		req.setAttribute("hotelList", list);
		req.getRequestDispatcher("/WEB-INF/jsp/listHotels.jsp").forward(req, resp);
	}
}
