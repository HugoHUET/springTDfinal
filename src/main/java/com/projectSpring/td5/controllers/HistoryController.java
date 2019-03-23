package com.projectSpring.td5.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.projectSpring.td5.entities.History;
import com.projectSpring.td5.entities.Script;
import com.projectSpring.td5.entities.User;
import com.projectSpring.td5.repositories.HistoriesRepository;
import com.projectSpring.td5.repositories.ScriptsRepository;

@Controller
@RequestMapping("/user/history/")
public class HistoryController {
	
	@Autowired
	private ScriptsRepository scriptsRepo;
	
	@Autowired
	private HistoriesRepository historiesRepo;
	
	@RequestMapping("{id}")
	public Object historique(ModelMap model, HttpSession session, @PathVariable int id) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			Optional<Script> optS = scriptsRepo.findById(id);
			if (optS.isPresent()) {
				Script s = optS.get();
				model.addAttribute("script", s);
				List<History> histo = historiesRepo.findAllByScript_id(id);
				model.addAttribute("histo", histo);
			}
			return "history/index";
		}else {
			return new RedirectView("../login");
		}
	}
	
	@RequestMapping("delete/{id}")
	public RedirectView deleteHisto(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			Optional<History> optH = historiesRepo.findById(id);
			History h = null;
			if(optH.isPresent()) {
				h = optH.get();
				historiesRepo.delete(h);
			}
			return new RedirectView("../"+h.getScript().getId());
		}else {
			return new RedirectView("../login");
		}
	}
	
	@RequestMapping("contentHistory/{id}")
	public Object getHisto(@PathVariable int id, ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			Optional<History> optH = historiesRepo.findById(id);
			System.out.println(optH.toString());
			if(optH.isPresent()) {
				History h = optH.get();
				model.addAttribute("histo", h);

				return "history/content";
			}
			return "";
		}else {
			return new RedirectView("../../login");
		}
	}

}
