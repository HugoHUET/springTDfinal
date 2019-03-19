package com.projectSpring.td5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/")
public class TestController {
	
	@RequestMapping("/index")
	public String ind(ModelMap model) {
		return "index";
	}

}
