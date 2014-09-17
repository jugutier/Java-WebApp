package ar.edu.itba.grupo2.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.grupo2.model.Comment;
import ar.edu.itba.grupo2.model.User;
import ar.edu.itba.grupo2.service.UserService;

@SuppressWarnings("serial")
public class UserComments extends BaseWebServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		super.doGet(req, resp);
		
		UserService userService = UserService.getInstance();
		User user = (User)req.getSession().getAttribute("user");
		
		List<Comment> commentList = userService.getCommentsByUser(user);
		
		req.setAttribute("commentList", commentList);
		req.getRequestDispatcher("/WEB-INF/jsp/userComments.jsp").forward(req, resp);
	}
}
