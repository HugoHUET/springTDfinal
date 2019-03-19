package com.projectSpring.td5.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.projectSpring.td5.entities.User;
import com.projectSpring.td5.repositories.UsersRepository;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UsersRepository usersRepo;
	
	@RequestMapping("create")
	public void crea(ModelMap model) {
		/*User user = new User();
		user.setEmail("machin@gmail.com");
		user.setIdentity("Michel");
		user.setLogin("login");
		user.setPassword("password");
		
		userRepo.save(user);*/
		
		//model.addAttribute("organisation", new User());
	}
	
	@RequestMapping("login")
	public String log(ModelMap model) {
		model.addAttribute("user", new User());
		return "user/login";
	}
	
	@PostMapping("login")
	public RedirectView add(@ModelAttribute("user") User user, HttpSession session) {
		User userLog = usersRepo.findByLogin(user.getLogin());
		User userPass = usersRepo.findByPassword(user.getPassword());
		
		if (userLog.equals(userPass)) {
			session.setAttribute("user", userLog);
			return new RedirectView("index");
		}
		return new RedirectView("login");
	}
	
	@RequestMapping("index")
	public String ind(ModelMap model, HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		return "user/index";
	}
	
	@RequestMapping("logout")
	public String log0ut(ModelMap model, HttpSession session) {
		session.setAttribute("name", null);
		return "user/index";
	}


}