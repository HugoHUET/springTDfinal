package com.projectSpring.td5.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.projectSpring.td5.entities.History;
import com.projectSpring.td5.entities.Script;
import com.projectSpring.td5.entities.User;
import com.projectSpring.td5.repositories.HistoriesRepository;
import com.projectSpring.td5.repositories.ScriptsRepository;
import com.projectSpring.td5.repositories.UsersRepository;

@Controller
@RequestMapping("/user/")
public class UserController {
	public boolean disconnect = false;
	
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private ScriptsRepository scriptsRepo;
	
	@Autowired
	private HistoriesRepository historiesRepo;
	
	@RequestMapping("create")
	public void crea(ModelMap model) {
		User user = usersRepo.findById(2);
		/*User user = new User();
		user.setEmail("machin@gmail.com");
		user.setIdentity("Michel");
		user.setLogin("login");
		user.setPassword("password");
		
		userRepo.save(user);*/
		
		Script script = new Script();
		script.setContent("ceci est le contenu du script");
		script.setDescription("description du fichier");
		script.setTitle("Big Project");

		script.setCreationData("today");
		script.setUser(user);
		scriptsRepo.save(script);
	}
	
	@RequestMapping("login")
	public String log(ModelMap model) {
		model.addAttribute("disconnect", disconnect);
		disconnect = false;
		model.addAttribute("user", new User());
		
		return "user/login";
	}
	
	@PostMapping("login")
	public RedirectView add(@ModelAttribute("user") User user, HttpSession session) {
		User userLog = usersRepo.findByLogin(user.getLogin());
		User userPass = usersRepo.findByPassword(user.getPassword());
		if(userLog != null && userPass != null) {
			if (userLog.equals(userPass)) {
				session.setAttribute("user", userLog);
				return new RedirectView("index");
			}
		}
		return new RedirectView("login");
	}
	
	@RequestMapping("index")
	public Object ind(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			model.addAttribute("user", user);
			List<Script> script = scriptsRepo.findAllByUser_id(user.getId());
			model.addAttribute("script", script);
			return "user/index";
		}else {
			return new RedirectView("login");
		}
	}
	
	@RequestMapping("logout")
	public RedirectView log0ut(ModelMap model, HttpSession session) {
		session.invalidate();
		disconnect = true;
		return new RedirectView("login");
	}
	
	@RequestMapping("delete/{id}")
	public RedirectView deleteScript(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			Optional<Script> optS = scriptsRepo.findById(id);
			if(optS.isPresent()) {
				Script s = optS.get();
				List<History> optH = historiesRepo.findAllByScript_id(s.getId());
				for (History history : optH) {
					historiesRepo.delete(history);
				}
				scriptsRepo.delete(s);
			}
			return new RedirectView("../index");
		}else {
			return new RedirectView("../login");
		}
	}
	
}