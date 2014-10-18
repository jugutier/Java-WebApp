package ar.edu.itba.grupo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserComments extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		/*
		UserService userService = UserServiceImpl.getInstance();
		UserManager userManager = new UserManagerImpl(req);
		User user = userManager.getUser();
		
		List<Comment> commentList = userService.getCommentsByUser(user);
		
		req.setAttribute("commentList", commentList);
		req.getRequestDispatcher("/WEB-INF/jsp/userComments.jsp").forward(req, resp);*/
	}
}
