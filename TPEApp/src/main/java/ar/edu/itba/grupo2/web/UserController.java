package ar.edu.itba.grupo2.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.service.UserService;
import ar.edu.itba.grupo2.service.impl.UserServiceImpl;
import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.session.UserManager;
import ar.edu.itba.grupo2.web.session.UserManagerImpl;

@Controller
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/*@RequestMapping(method=RequestMethod.GET)
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		
		//UserManager userManager = new UserManagerImpl((HttpServletRequest)req);
		//userManager.resetUser();
		
		mav.setViewName("welcome");
		
		return mav;
	}*/
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("register");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String registerUser(@RequestParam(value = "email", required=true) String email, 
			@RequestParam(value = "name", required=true) String name,
			@RequestParam(value = "lastname", required=true) String lastname,
			@RequestParam(value = "password", required=true) String password,
			@RequestParam(value = "passwordConfirm", required=true) String passwordConfirm,
			@RequestParam(value = "secretQuestion", required=true) String secretQuestion,
			@RequestParam(value = "secretAnswer", required=true) String secretAnswer,
			@RequestParam(value = "birthdate", required=true) String birthday) {
		
		List<String> errors = new ArrayList<String>();
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = null;
		String target;
		try {
			birthdate = outputDateFormat.parse(birthday);
		} catch (ParseException e) {
			errors.add("WrongDate");
		} finally {
			errors.addAll(validate(email, name, lastname, password,
					passwordConfirm, secretQuestion, secretAnswer, birthdate));
			if (errors.size() != 0) {
				//req.setAttribute("errors", errors);
				//this.doGet(req, resp);
				target = "redirect:register";
			} else {
				//UserManager userManager = new UserManagerImpl(req);
				//loggedUser=UserServiceImpl.getInstance().registerUser(email, password,
				//		passwordConfirm, name, lastname, birthdate,
				//		secretQuestion, secretAnswer);
				//userManager.setUser(loggedUser);
				target = "redirect:../film/welcome";
			}
		}
		
		return target;
	}
	
	private List<String> validate(String email, String name, String lastname,
			String password, String passwordConfirm, String secretQuestion,
			String secretAnswer, Date birthdate) {
		List<String> errors = new ArrayList<String>();
		if (ValidationUtilities.paramEmpty(email)) {
			errors.add("NoMail");
		} else if (!ValidationUtilities.isEmail(email)) {
			errors.add("InvalidMail");
		}
		if (ValidationUtilities.paramEmpty(name)) {
			errors.add("NoName");
		}
		if (ValidationUtilities.paramEmpty(lastname)) {
			errors.add("NoLastname");
		}
		if (ValidationUtilities.paramEmpty(password)) {
			errors.add("NoPass");
		} else if (!password.equals(passwordConfirm)) {
			errors.add("NoCoincidence");
		}
		if (ValidationUtilities.paramEmpty(secretQuestion)) {
			errors.add("NoSQ");
		}
		if (ValidationUtilities.paramEmpty(secretAnswer)) {
			errors.add("NoSA");
		}
		if (UserServiceImpl.getInstance().existsUser(email)) {
			errors.add("MailUsed");
		}

		return errors;

	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView authenticateUser() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("welcome");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView resetPassword() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("resetPassword");
		
		return mav;
	}
}
