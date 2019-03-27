package com.projectSpring.td5.controllers;

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
	@PostMapping("getScripts")
	public String getScript(@RequestBody Recherche search){
		//User user = (User) session.getAttribute("user");
		//if(user != null) {
			System.out.println("recherche : ");
			return "ok";
			//return scriptsRepo.findAllByUser_id(user.getId());
		//}else {
		//	return null;
		//}
		
	}
}
