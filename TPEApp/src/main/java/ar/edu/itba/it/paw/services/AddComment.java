package ar.edu.itba.it.paw.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.API;
import ar.edu.itba.it.paw.model.UserManager;

@SuppressWarnings("serial")
public class AddComment extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		int hotel_id = Integer.valueOf(req.getParameter("hotel_id"));
		String message = req.getParameter("message");
		UserManager userManager = new UserManager(req);
		
		if(userManager.existsUser()){
			API.addCommentToHotel(hotel_id, userManager.getName(), message);
			
			resp.sendRedirect(resp.encodeRedirectURL("viewHotel?id=" + hotel_id));
		}	
	}
}
