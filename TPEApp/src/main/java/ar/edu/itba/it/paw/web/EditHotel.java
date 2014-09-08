package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.Hotel;
import ar.edu.itba.it.paw.model.Utilities;

@SuppressWarnings("serial")
public class EditHotel extends HttpServlet{
	
	@Override
	public void init() throws ServletException{
		super.init();
		
		API.createBogusDatabase();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		int id = Integer.valueOf(Utilities.getQueryMap(req.getQueryString()).get("id"));
		
		Hotel hotel = API.getHotelById(id);
		
		req.setAttribute("action", "editHotelService");
		req.setAttribute("hotel", hotel);
		req.getRequestDispatcher("/WEB-INF/jsp/editHotel.jsp").forward(req, resp);
	}
}
