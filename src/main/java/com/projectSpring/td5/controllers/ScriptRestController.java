package com.projectSpring.td5.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectSpring.td5.Recherche;
import com.projectSpring.td5.entities.Script;
import com.projectSpring.td5.repositories.ScriptsRepository;

@RestController
@RequestMapping("/search/")
public class ScriptRestController {
	
	@Autowired
	private ScriptsRepository scriptsRepo;
	
	@ResponseBody
	@PostMapping("search")
	public List<Script> getScript(@RequestBody Recherche search){
		List<Script> allScript = scriptsRepo.findAll();
		List<Script> scriptContains = new ArrayList<Script>();
		for (Script s : allScript) {
			if(s.getTitle().toUpperCase().contains(search.getRecherche().toUpperCase())) {
				scriptContains.add(s);
			}
		}
		return scriptContains;
		
	}
}
