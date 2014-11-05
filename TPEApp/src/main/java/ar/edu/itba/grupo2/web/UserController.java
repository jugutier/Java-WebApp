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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.utils.ValidationUtilities;
import ar.edu.itba.grupo2.web.command.UserForm;
import ar.edu.itba.grupo2.web.command.validator.UserFormValidator;

@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {
	
	private final UserFormValidator userValidator;
	
	@Autowired
	public UserController(UserRepo userRepo,UserFormValidator userValidator) {
		super(userRepo);
		this.userValidator=userValidator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String logout(HttpSession session) {
		logOut(session);
		
		return "redirect:../film/welcome";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User logged = getLoggedInUser(session);
		List<User> toFollow = userRepo.getAll();
		mav.addObject("toFollow", toFollow);
		mav.setViewName("userList");
		
		return mav;
	}
	
	@RequestMapping(value = "{id}/profile",method=RequestMethod.GET)
	public ModelAndView profile(HttpSession session,@PathVariable(value = "id") User user) {
		ModelAndView mav = new ModelAndView();
		User u = getLoggedInUser(session);
		user.canBeFollowed(u);
		mav.addObject("user", user);
		mav.addObject("comments", user.getComments());
		mav.setViewName("userProfile");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		mav.setViewName("register");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String register(UserForm userForm, Errors errors,HttpSession session) {
		User newUser=null;
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate=null;
		userValidator.validate(userForm, errors);
		if(userRepo.getUserByEmail(userForm.getEmail())!= null){
			errors.rejectValue("email", "emailExists");;
		}
		 try {
		birthdate = outputDateFormat.parse(userForm.getBirthdate());
		} catch (ParseException e) {
			errors.rejectValue("birthdate", "invalid");
		}
		if(errors.hasErrors()){
			return "register";
		}
		else{
			newUser=userRepo.registerUser(userForm.getEmail(), userForm.getPassword(), userForm.getPasswordConfirm(), userForm.getName(), userForm.getLastname(), birthdate, userForm.getSecretQuestion(), userForm.getSecretAnswer());
			this.setLoggedInUser(session, newUser);
			return "redirect:../film/welcome";
		}
		 
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
	
	@RequestMapping(value="resetPassword", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView resetPassword(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
			@RequestParam(value = "answer", required = false) String answer,
			@RequestParam(value = "stage", required = false) String stage) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("resetPassword");
		
		List<String> errors = new ArrayList<String>();

		if (stage != null && stage.compareTo("getEmail") == 0) {
			errors.addAll(validateEmailStage(email));
			
			if (errors.isEmpty()) {
				// Display Secret Question
				mav.addObject("email", email);
				mav.addObject("secretQuestion", userRepo.getUserByEmail(email).getSecretQuestion());
				mav.addObject("stage", "question");
			}
		}
		else if (stage != null && stage.compareTo("question") == 0){
			errors.addAll(validateAnswerStage(email, answer, password, passwordConfirm));
			
			if (errors.isEmpty()) {
				mav.setViewName("redirect:../film/welcome");
			}
		}
		else {
			mav.addObject("stage", "getEmail");
		}

		if (!errors.isEmpty()) {
			mav.addObject("errors", errors);
			mav.addObject("stage", "getEmail");
		}
		
		/*UserManager userManager = new UserManagerImpl((HttpServletRequest)req);
		userManager.resetUser();
		
		resp.sendRedirect(resp.encodeRedirectURL("welcome"));*/
		return mav;
	}
	
	private List<String> validateEmailStage(String email) {
		List<String> errors = new ArrayList<String>();

		if (ValidationUtilities.paramEmpty(email)) {
			errors.add("noMail");
		}
		else if (!ValidationUtilities.isEmail(email)) {
			errors.add("invalidMail");
		}
		else if (userRepo.getUserByEmail(email) == null){
			errors.add("wrongMail");
		}

		return errors;
	}

	private List<String> validateAnswerStage(String email, String answer,
			String password, String passwordConfirm) {
		List<String> errors = new ArrayList<String>();
		User user = userRepo.getUserByEmail(email);

		errors.addAll(validateEmailStage(email));

		if (ValidationUtilities.paramEmpty(password)) {
			errors.add("noPassword");
		}
		if (ValidationUtilities.paramEmpty(passwordConfirm)) {
			errors.add("noPasswordConfirm");
		}
		if (password.compareTo(passwordConfirm) != 0) {
			errors.add("noCoincidence");
		}
		if (ValidationUtilities.paramEmpty(answer)) {
			errors.add("noAnswer");
		}
		else if (!user.resetPassword(answer, passwordConfirm)) {
			errors.add("wrongAnswer");
		}

		return errors;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView userComments(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		User user = getLoggedInUser(session);
		
		mav.addObject("commentList", user.getComments());
		
		mav.setViewName("userComments");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String follow(HttpSession session,@RequestParam(value = "fromPage", required=true) String fromPage, @RequestParam(value = "id", required=true) User user){
		User loggedUser = getLoggedInUser(session);
		loggedUser.followUser(user);
		return "redirect:" + fromPage;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String unFollow(HttpSession session,@RequestParam(value = "fromPage", required=true) String fromPage, @RequestParam(value = "id", required=true) User user){
		User loggedUser = getLoggedInUser(session);
		loggedUser.unFollowUser(user);
		return "redirect:"+fromPage;
	}
	
}
