package com.codingdojo.exam.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.exam.models.Idea;
import com.codingdojo.exam.models.User;
import com.codingdojo.exam.models.UserIdea;
import com.codingdojo.exam.services.MainService;
import com.codingdojo.exam.validations.UserValidator;

@Controller
public class MainController {
	
	private final MainService mainService;
	private final UserValidator userValidator;
	public MainController(MainService mainService,UserValidator userValidator) {
		this.mainService = mainService;
		this.userValidator=userValidator;
	}
	
	@RequestMapping("/")
	public String homepage(@ModelAttribute("user")User user) {
		
		return "index.jsp";
	}
	
	@RequestMapping( value = "/registration", method=RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("user")User user,BindingResult result,HttpSession session) {
		
		System.out.println("*******************");
		System.out.println(user.getEmail());
		System.out.println("*******************");

//		System.out.println(this.userService.findByEmail(user.getEmail()));
		userValidator.validate(user,result);
		if(result.hasErrors()) {
			return "index.jsp";
		}
		//TODO later after login reg works prevent dupe emails
		
		// create a user with this information
		
		User userObj = this.mainService.registerUser(user);
		//get the user that just got created and store their id in session
		session.setAttribute("userid", userObj.getId());
		return "redirect:/dashboard";
		
	}
	
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		 //clear the session
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String login(@RequestParam("email")String email,@RequestParam("password")String password,HttpSession session,RedirectAttributes redirectAttributes) {
			Boolean isConfirmed = this.mainService.authenticateUser(email, password);
			
			if(isConfirmed) {
				//if the email password combo is correct,log them in using session and redirect them to the dashboard
				
				//get the user with that email
				User user= this.mainService.findByEmail(email);
				
				//put that user id in session
				session.setAttribute("userid", user.getId());
				return "redirect:/dashboard";
			}
			//if login attempt was unsuccessful, flash an error message
			redirectAttributes.addFlashAttribute("error","Invalid login");
			return "redirect:/";			
		}
	
	//////////Rest of the Exam starts here\\\\\\\\\\\\\

	@RequestMapping("/dashboard")
	public String dashboard(Model model,HttpSession session) {
		//retrieve the userobject from the db whos id matches the id stored in session
		Long id = (Long)session.getAttribute("userid");
		User loggedinuser = this.mainService.findUserById(id);
		
		model.addAttribute("loggedinuser",loggedinuser);
		model.addAttribute("allideas",this.mainService.findAllIdeas());
		return "dashboard.jsp";
	}
	
	@RequestMapping("/ideas/new")
	public String newIdea(@ModelAttribute("idea")Idea idea) {
		return "newidea.jsp";
	}
	
	@RequestMapping(value = "/ideas/create", method=RequestMethod.POST)
	public String createIdea(@Valid @ModelAttribute("idea")Idea idea,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			return "newidea.jsp";
		}else {
			Long id = (Long)session.getAttribute("userid");
			User loggedinuser = this.mainService.findUserById(id);
			
			idea.setCreator(loggedinuser);
			
			this.mainService.createAIdea(idea);
			return "redirect:/dashboard";
		}
	}
	@RequestMapping("/ideas/{id}")
	public String showIdea(@PathVariable("id")Long id,Model model) {
		this.mainService.findOneIdea(id);
		model.addAttribute("ideaToShow",this.mainService.findOneIdea(id));
		
		return "showidea.jsp";
	}
	
	@RequestMapping("ideas/{id}/edit")
	public String editAIdea(@PathVariable("id")Long id, Model model, @ModelAttribute("idea")Idea idea) {
		this.mainService.findOneIdea(id);
		model.addAttribute("idea",this.mainService.findOneIdea(id));
		
		return "editidea.jsp";
	}
	@RequestMapping(value ="ideas/update/{id}",method = RequestMethod.POST)
	public String updateAIdea(@PathVariable("id")Long id,@Valid @ModelAttribute("idea")Idea idea,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			return "editidea.jsp";
		}else {
			
			Long loggedinuserid = (Long)session.getAttribute("userid");
			User loggedinuser = this.mainService.findUserById(loggedinuserid);
			
			idea.setCreator(loggedinuser);
			
			this.mainService.updateAIdea(idea);
			return "redirect:/dashboard";
		}
		
		
	}
	
	@RequestMapping("/ideas/{id}/delete")
	public String deleteAIdea(@PathVariable("id")Long id) {
		
		Idea i = this.mainService.findOneIdea(id);
		
		this.mainService.deleteAIdea(i);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/ideas/{id}/like")
	public String likeAnIdea(@PathVariable("id")Long id,HttpSession session) {
		Idea i = this.mainService.findOneIdea(id);
		
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.mainService.findUserById(loggedinuserid);
		
		if(i.getUsersWholiked().contains(loggedinuser)) {
			
			System.out.println("You have already like this idea!");
			return "redirect:/dashboard";
		}else {
			UserIdea userLike = new UserIdea(loggedinuser,i);
			this.mainService.createAssociation(userLike);

		}
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/ideas/{id}/unlike")
		public String unlikeAnIdea(@PathVariable("id")Long id,HttpSession session) {
Idea i = this.mainService.findOneIdea(id);
		
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.mainService.findUserById(loggedinuserid);
		
		if(!i.getUsersWholiked().contains(loggedinuser)) {
			
			System.out.println("You can not unlike this post as you have not liked it yet");
			return "redirect:/dashboard";
		}else {
			
			loggedinuser.getIdeasLiked().remove(i);
			
			this.mainService.unlikeIdea(loggedinuser);
			
			return "redirect:/dashboard";

		}
	}
	
	
}
