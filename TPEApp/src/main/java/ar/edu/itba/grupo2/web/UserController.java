package ar.edu.itba.grupo2.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.utils.ValidationUtilities;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	public UserController(UserRepo userRepo) {
		super(userRepo);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String logout(HttpSession session) {
		logOut(session);
		
		return "redirect:../film/welcome";
	}
	
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
		if (userRepo.getUserByEmail(email)!=null) {
			errors.add("MailUsed");
		}

		return errors;

	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String authenticateUser(
			HttpSession session,
			@RequestParam(value = "email", required=true) String email,
			@RequestParam(value = "password", required=true) String password,
			@RequestParam(value = "fromPage", required=true) String fromPage) {
		
		User loggedUser = null;
		
		String ret = null;
		
		if (fromPage == null) {
			fromPage = "welcome";
		}
		
		loggedUser = userRepo.logIn(email, password);
		
		if (loggedUser != null) {
			session.setAttribute("userId", loggedUser.getId());
			//userManager.setUser(loggedUser);
			ret = "redirect:" + fromPage.replace("auth_fail=wrongUser", "");
		}
		else{
			char separator;
			if (fromPage.contains("?")) {
				separator = '&';
			}
			else {
				separator = '?';
			}
			if (fromPage.contains("auth_fail=wrongUser")) {
				ret = "redirect:" + fromPage;
			}
			else {
				ret = "redirect:" + fromPage + separator + "auth_fail=wrongUser";
			}
			
		}
		
		return ret;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView resetPassword() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("resetPassword");
		/*UserManager userManager = new UserManagerImpl((HttpServletRequest)req);
		userManager.resetUser();
		
		resp.sendRedirect(resp.encodeRedirectURL("welcome"));*/
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView userComments(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		User user = getLoggedInUser(session);
		
		mav.addObject("commentList", user.getComments());
		
		mav.setViewName("userComments");;
		
		return mav;
	}
}

/*UserService userService = UserServiceImpl.getInstance();
String email = req.getParameter("email");
String password = req.getParameter("password");
String passwordConfirm = req.getParameter("passwordConfirm");
String answer = req.getParameter("answer");
String stage = req.getParameter("stage");

List<String> errors = new ArrayList<String>();

if (stage != null && stage.compareTo("getEmail") == 0) {
	errors.addAll(validateEmailStage(email));
	
	if (errors.isEmpty()) {
		// Display Secret Question
		req.setAttribute("email", email);
		req.setAttribute("secretQuestion", userService.getSecretQuestionForEmail(email));
		req.setAttribute("stage", "question");
	}
}
else if (stage != null && stage.compareTo("question") == 0){
	errors.addAll(validateAnswerStage(email, answer, password, passwordConfirm));
	
	if (errors.isEmpty()) {
		resp.sendRedirect(resp.encodeRedirectURL("home"));
		return;
	}
}
else {
	doGet(req, resp);
}

if (!errors.isEmpty()) {
	req.setAttribute("errors", errors);
	doGet(req, resp);
}
else {
	req.getRequestDispatcher("/WEB-INF/jsp/resetPassword.jsp").forward(req, resp);
}
}*/


/*private List<String> validateEmailStage(String email) {
List<String> errors = new ArrayList<String>();
UserService userService = UserServiceImpl.getInstance();

if (ValidationUtilities.paramEmpty(email)) {
	errors.add("noMail");
}
else if (!ValidationUtilities.isEmail(email)) {
	errors.add("invalidMail");
}
else if (!userService.existsUser(email)){
	errors.add("wrongMail");
}

return errors;
}

private List<String> validateAnswerStage(String email, String answer,
	String password, String passwordConfirm) {
List<String> errors = new ArrayList<String>();
UserService userService = UserServiceImpl.getInstance();

errors.addAll(validateEmailStage(email));

if (ValidationUtilities.paramEmpty(answer)) {
	errors.add("noAnswer");
}
else if (!userService.resetPasswordForEmail(email, passwordConfirm, answer)) {
	errors.add("wrongAnswer");
}
if (ValidationUtilities.paramEmpty(password)) {
	errors.add("noPassword");
}
if (ValidationUtilities.paramEmpty(passwordConfirm)) {
	errors.add("noPasswordConfirm");
}
if (password.compareTo(passwordConfirm) != 0) {
	errors.add("noCoincidence");
}

return errors;
}*/
