package ar.edu.itba.it.paw.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.UserManager;

@SuppressWarnings("serial")
public class AddHotelService extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String hotel_name = req.getParameter("hotel_name");
		String hotel_description = req.getParameter("hotel_description");
		
		UserManager userManager = new UserManager(req);
		
		if(userManager.existsUser()){
			API.addHotel(hotel_name, hotel_description);
			
			resp.sendRedirect(resp.encodeRedirectURL("listHotels"));
		}
	}
}