package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserManager;
import ar.edu.itba.grupo2.service.UserService;
import ar.edu.itba.grupo2.service.impl.UserManagerImpl;
import ar.edu.itba.grupo2.service.impl.UserServiceImpl;

@SuppressWarnings("serial")
public class UserComments extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		UserService userService = UserServiceImpl.getInstance();
		UserManager userManager = new UserManagerImpl(req);
		User user = userManager.getUser();
		
		List<Comment> commentList = userService.getCommentsByUser(user);
		
		req.setAttribute("commentList", commentList);
		req.getRequestDispatcher("/WEB-INF/jsp/userComments.jsp").forward(req, resp);
	}
}
