package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.Hotel;

@SuppressWarnings("serial")
public class AddHotel extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		API.createBogusDatabase();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Hotel hotel = new Hotel(-1, "", "");
		
		req.setAttribute("action", "addHotelService");
		req.setAttribute("hotel", hotel);
		req.getRequestDispatcher("/WEB-INF/jsp/editHotel.jsp").forward(req, resp);
	}
}
