package com.projectSpring.td5.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.projectSpring.td5.entities.Categorie;
import com.projectSpring.td5.entities.History;
import com.projectSpring.td5.entities.Language;
import com.projectSpring.td5.entities.Script;
import com.projectSpring.td5.entities.User;
import com.projectSpring.td5.repositories.CategoriesRepository;
import com.projectSpring.td5.repositories.HistoriesRepository;
import com.projectSpring.td5.repositories.LanguagesRepository;
import com.projectSpring.td5.repositories.ScriptsRepository;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;

@Controller
@RequestMapping("/user/script/")
public class ScriptController {
	
	@Autowired
	private ScriptsRepository scriptsRepo;

	@Autowired
	private LanguagesRepository languagesRepo;
	
	@Autowired
	private CategoriesRepository categoriesRepo;
	
	@Autowired
	private HistoriesRepository historiesRepo;
	
	@Autowired
	private VueJS vue;
	
	@GetMapping("new")
	public Object crea(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			model.addAttribute("user", new Script());
			model.addAttribute("lang", languagesRepo.findAll());
			model.addAttribute("categ", categoriesRepo.findAll());
			return "script/new";
		}else {
			return new RedirectView("../login");
		}
	}
	
	@PostMapping("new")
	public RedirectView ajout(@ModelAttribute("script") Script script, HttpSession session, @RequestParam int categorieId, @RequestParam int languageId) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		
		Script s = new Script();
		s.setCategorie(categoriesRepo.findById(categorieId));
		s.setContent(script.getContent());
		s.setCreationData(dateFormat.format(date));
		s.setDescription(script.getDescription());
		s.setTitle(script.getTitle());
		s.setLanguage(languagesRepo.findById(languageId));
		User user = (User) session.getAttribute("user");
		s.setUser(user);
		scriptsRepo.save(s);
		
		History historique = new History();
		historique.setComment("création du projet");
		historique.setContent(script.getContent());
		historique.setDate(dateFormat.format(date));
		historique.setScript(s);
		
		historiesRepo.save(historique);
		
		return new RedirectView("../index");
	}
	
	@GetMapping("{id}")
	public Object getScript(@PathVariable int id, ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null) {
			Optional<Script> opt = scriptsRepo.findById(id);
			if(opt.isPresent()) {
				Script s = opt.get();
				if(user.getId() == s.getUser().getId()) {
					model.addAttribute("script", s);
					
					List<Language> liLang = languagesRepo.findAll();
					liLang.remove(s.getLanguage());
					model.addAttribute("lang", liLang);
					
					List<Categorie> liCat = categoriesRepo.findAll();
					liCat.remove(s.getCategorie());
					
					model.addAttribute("lang", liLang);
					model.addAttribute("categ", liCat);
					return "script/update";
				}else {
					return "script/redirect";
				}
			}
			return "";
		}else {
			return new RedirectView("../login");
		}
	}
	
	@PostMapping("{id}")
	public RedirectView modif(@ModelAttribute("script") Script script, @PathVariable int id, ModelMap model, @RequestParam int categorieId, @RequestParam int languageId, @RequestParam String comment) {
		Optional<Script> opt = scriptsRepo.findById(id);
		if(opt.isPresent()) {
			Script s = opt.get();
			model.addAttribute("script", s);
			
			History historique = new History();
			historique.setComment(comment);
			historique.setContent(script.getContent());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			historique.setDate(dateFormat.format(date));
			historique.setScript(script);
			
			historiesRepo.save(historique);
			
			s.setContent(script.getContent());
			s.setDescription(script.getDescription());
			s.setTitle(script.getTitle());
			s.setLanguage(languagesRepo.findById(languageId));
			
			scriptsRepo.save(s);
		}
		
		return new RedirectView("../index"); 
	}
	
	@GetMapping("search")
	public String test(Model model) {
		model.addAttribute("vue", vue);
		vue.addData("search", "");
		vue.addMethod("test", "var self=this;"+Http.post(
				"/search/getScripts", 
				(Object)"{recherche:self.search}",
				"console.log(self.search)")
		);
		
		
		return "vueJS/search";
	}
	
}
